package com.huameng.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkPageRespDto;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

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
}
