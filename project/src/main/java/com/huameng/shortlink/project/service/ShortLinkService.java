package com.huameng.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkUpdateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkPageRespDto;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO>{

    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    ShortLinkCreateRespDto createShortLink(ShortLinkCreateReqDto requestParam);

    /**
     * 短链接分组查询
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDto> pageShortLink(ShortLinkPageReqDto requestParam);

    /**
     * 查询短链接分组内数量
     * @param requestParam
     * @return
     */
    List<ShortLinkGroupCountQueryRespDto> listGroupShortLinkCount(List<String> requestParam);

    /**
     * 修改短链接相关信息
     * @param requestParam
     */
    void updateShortLink(ShortLinkUpdateReqDto requestParam);

    /**
     * 短链接跳转
     * @param shortUri 短链接后缀
     * @param request 请求
     * @param response 响应
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) throws IOException;
}
