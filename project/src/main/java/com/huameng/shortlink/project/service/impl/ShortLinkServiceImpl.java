package com.huameng.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huameng.shortlink.project.common.convention.exception.ClientException;
import com.huameng.shortlink.project.common.convention.exception.ServiceException;
import com.huameng.shortlink.project.common.enums.ValidDateTypeEnum;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dao.entity.ShortLinkGotoDO;
import com.huameng.shortlink.project.dao.mapper.ShortLinkGotoMapper;
import com.huameng.shortlink.project.dao.mapper.ShortLinkMapper;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkUpdateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkPageRespDto;
import com.huameng.shortlink.project.service.ShortLinkService;
import com.huameng.shortlink.project.toolkit.HashUtil;
import com.huameng.shortlink.project.toolkit.LinkUtil;
import groovy.util.logging.Slf4j;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.huameng.shortlink.project.common.constant.RedisKeyConstant.*;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;
    private final ShortLinkGotoMapper shortLinkGotoMapper;

    private final StringRedisTemplate stringRedisTemplate;

    //分布式锁
    private final RedissonClient redissonClient;

    @Override
    public ShortLinkCreateRespDto createShortLink(ShortLinkCreateReqDto requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        String fullShortUrl = requestParam.getDomain() + "/" + shortLinkSuffix;
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(0)
                .fullShortUrl(fullShortUrl)
                .build();
        //短链接跳转
        ShortLinkGotoDO linkGotoDO = ShortLinkGotoDO.builder()
                .fullShortUri(fullShortUrl)
                .gid(requestParam.getGid())
                .build();
        try{
            baseMapper.insert(shortLinkDO);
            shortLinkGotoMapper.insert(linkGotoDO);
        }catch(DuplicateKeyException e){
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO shortLinkDO1 = baseMapper.selectOne(queryWrapper);
            if(shortLinkDO1 != null){
                log.warn("短链接：重复入库");
                throw new ServiceException("短链接生成重复");
            }

        }
        //缓存预热
        stringRedisTemplate.opsForValue()
                .set(
                        String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                        requestParam.getOriginUrl(),
                        LinkUtil.getLinkCacheValidDate(requestParam.getValidDate()),
                        TimeUnit.MILLISECONDS
                );
        shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return ShortLinkCreateRespDto.builder()
                .fullShortUrl("http://" + shortLinkDO.getFullShortUrl())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .build();
    }

    private String generateSuffix(ShortLinkCreateReqDto requestParam){
        int customGenerateCount = 0;
        String shortUri;
        while(true){
            if(customGenerateCount > 10){
                throw new ServiceException("短链接频繁生成，请稍后再试");
            }
            String originUrl = requestParam.getOriginUrl();
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            if(!shortUriCreateCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)){
                break;
            }
            customGenerateCount++;
        }

        return shortUri;
    }

    @Override
    public IPage<ShortLinkPageRespDto> pageShortLink(ShortLinkPageReqDto requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> result = baseMapper.selectPage(requestParam, queryWrapper);
        return result.convert(each-> {
            ShortLinkPageRespDto resultPage = BeanUtil.toBean(each, ShortLinkPageRespDto.class);
            resultPage.setDomain("http://" + resultPage.getDomain());
            return resultPage;
        });

    }

    @Override
    public List<ShortLinkGroupCountQueryRespDto> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam) {
        QueryWrapper<ShortLinkDO> queryWrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid, count(*) as shortLinkCount")
                .in("gid", requestParam)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkDOList = baseMapper.selectMaps(queryWrapper);
        return BeanUtil.copyToList(shortLinkDOList, ShortLinkGroupCountQueryRespDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDto requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if(hasShortLinkDO == null){
            throw new ClientException("短链接不存在");
        }
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(hasShortLinkDO.getDomain())
                .shortUri(hasShortLinkDO.getShortUri())
                .clickNum(hasShortLinkDO.getClickNum())
                .favicon(hasShortLinkDO.getFavicon())
                .createdType(hasShortLinkDO.getCreatedType())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .describe(requestParam.getDescribe())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .build();
        if(Objects.equals(hasShortLinkDO.getGid(), requestParam.getGid())){
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, requestParam.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .set(Objects.equals(requestParam.getValidDate(), ValidDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.update(shortLinkDO, updateWrapper);
        }else{
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requestParam.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, hasShortLinkDO.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .set(Objects.equals(requestParam.getValidDate(), ValidDateTypeEnum.PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.delete(updateWrapper);
            baseMapper.insert(shortLinkDO);

        }
    }

    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) throws IOException {
        String serverName = request.getServerName();
        String fullShortUrl = serverName + "/" + shortUri;
        /**
         * String.format(GOTO_SHORT_LINK_KEY, fullShortUrl)：这里使用String.format方法将fullShortUrl参数插入到格式化字符串中。GOTO_SHORT_LINK_KEY是一个静态常量，其值为short-link_goto_%s，其中%s表示占位符，将被fullShortUrl替换。
         * stringRedisTemplate.opsForValue().get(...)：stringRedisTemplate是Spring提供的用于操作Redis的模板类。在这里，调用opsForValue()方法返回一个ValueOperations对象，用于操作Redis中的字符串类型数据。
         * get(...)方法用于从Redis中获取指定key对应的值。在这里，该方法使用String.format得到的字符串作为key，以获取存储在Redis中的原始链接。
         * 这段代码的作用是根据给定的短链接（fullShortUrl）生成对应的Redis key，然后从Redis中获取该key对应的原始链接（originLink）。这种方式可以实现根据短链接快速查找原始链接的功能。
         */
        String originLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
        if(StrUtil.isNotBlank(originLink)){
            ((HttpServletResponse) response).sendRedirect(originLink);
            return;
        }
        //判断布隆过滤器中是否存在
        //存在：返回
        if(!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)){
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }

        //不存在：
        String gotoIsNullShortLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl));
        if(StringUtil.isNotBlank(gotoIsNullShortLink)){
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }


        //分布式锁防止缓存击穿
        RLock lock = redissonClient.getLock(String.format(LOCK_GOTO_SHORT_LINK_KEY, fullShortUrl));
        lock.lock();
        try{
                //双重判定：由于在获取锁之前可能有其他线程已经设置了原始链接，因此需要再次检查以避免重复操作
                originLink = stringRedisTemplate.opsForValue().get(String.format(GOTO_SHORT_LINK_KEY, fullShortUrl));
                if(StrUtil.isNotBlank(originLink)){
                    ((HttpServletResponse) response).sendRedirect(originLink);
                    return;
                }
                LambdaQueryWrapper<ShortLinkGotoDO> linkGotoQueryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                        .eq(ShortLinkGotoDO::getFullShortUri, fullShortUrl);
                ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(linkGotoQueryWrapper);
                if(shortLinkGotoDO == null){
                    stringRedisTemplate.opsForValue().set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.MINUTES);
                    ((HttpServletResponse) response).sendRedirect("/page/notfound");
                    //风控
                    return;
                }
                LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                        .eq(ShortLinkDO::getGid, shortLinkGotoDO.getGid())
                        .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                        .eq(ShortLinkDO::getDelFlag, 0)
                        .eq(ShortLinkDO::getEnableStatus, 0);
                ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
                if(shortLinkDO != null){
                    //查到的短链接如果已经过期
                    if(shortLinkDO.getValidDate() != null && shortLinkDO.getValidDate().before(new Date())){
                        stringRedisTemplate.opsForValue().set(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, fullShortUrl), "-", 30, TimeUnit.MINUTES);
                        ((HttpServletResponse) response).sendRedirect("/page/notfound");
                        return;
                    }
                    //跳转前将链接添加到缓存中（缓存预热）
                    stringRedisTemplate.opsForValue()
                            .set(
                                    String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
                                    shortLinkDO.getOriginUrl(),
                                    LinkUtil.getLinkCacheValidDate(shortLinkDO.getValidDate()),
                                    TimeUnit.MILLISECONDS
                            );
                    ((HttpServletResponse) response).sendRedirect(shortLinkDO.getOriginUrl());
                }
            }finally {
                lock.unlock();
            }


    }
}
