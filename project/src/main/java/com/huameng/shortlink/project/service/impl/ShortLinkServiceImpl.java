package com.huameng.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dao.mapper.ShortLinkMapper;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.project.service.ShortLinkService;
import com.huameng.shortlink.project.toolkit.HashUtil;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {
    @Override
    public ShortLinkCreateRespDto createShortLink(ShortLinkCreateReqDto requestParam) {
        String shortLinkSuffix = generateSuffix(requestParam);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requestParam, ShortLinkDO.class);
        shortLinkDO.setShortUri(shortLinkSuffix);
        shortLinkDO.setFullShortUrl(requestParam.getDomain() + "/" + shortLinkSuffix);
        baseMapper.insert(shortLinkDO);
        return ShortLinkCreateRespDto.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .build();
    }

    private String generateSuffix(ShortLinkCreateReqDto requestParam){
        String originUrl = requestParam.getOriginUrl();
        return HashUtil.hashToBase62(originUrl);
    }
}
