package com.huameng.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.dto.req.RecycleBinSaveReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkPageRespDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

    /**
     * 分页查询
     * @param requestParam
     * @return
     */
    default Result<IPage<ShortLinkPageRespDto>> pageShortLink(ShortLinkPageReqDto requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", requestParam.getGid());
        requestMap.put("current", requestParam.getCurrent());
        requestMap.put("size", requestParam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 创建短链接
     * HttpUtil.post方法返回的结果是一个字符串，表示HTTP请求的响应体。
     * 使用JSON.parseObject方法将响应体字符串resultBodyStr解析为一个对象。在这里，使用了TypeReference匿名子类来指定要解析的对象类型，通过new TypeReference<>() {...}来实现。这样可以确保正确地将JSON字符串转换为ShortLinkCreateRespDto对象。
     * 最终，方法返回解析后的ShortLinkCreateRespDto对象，该对象包含了从目标URL返回的数据，通常包括新创建的短链接信息。
     */
    default Result<ShortLinkCreateRespDto> createShortLink(ShortLinkCreateReqDto requestParam){
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/create", JSON.toJSONString(requestParam));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 查询分组短链接数量
     * @param requestParam
     * @return
     */
    default Result<List<ShortLinkGroupCountQueryRespDto>> listGroupShortLinkCount(List<String> requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestParam", requestParam);
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/count", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 修改短链接相关信息
     * @param requestParam
     */
    default void updateShortLink(ShortLinkUpdateReqDto requestParam){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/update", JSON.toJSONString(requestParam));
    }

    /**
     * 根据 URL 获取标题
     * @param url
     * @return
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url){
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 保存回收站
     * @param requestParam
     * @return
     */
    default void saveRecycleBin(RecycleBinSaveReqDto requestParam){
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/recycle-bin/save", JSON.toJSONString(requestParam));
    }

    /**
     * 分页查询回收站短链接
     * @param requestParam
     * @return
     */
    default Result<IPage<ShortLinkPageRespDto>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDto requestParam){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gidList", requestParam.getGidList());
        requestMap.put("current", requestParam.getCurrent());
        requestMap.put("size", requestParam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/recycle-bin/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

}
