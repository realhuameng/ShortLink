package com.huameng.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkPageRespDto;

/**
 * 链接回收站接口层
 */
public interface RecycleBinService {

    /**
     * 分页查询回收站内的链接
     * @param requestParam
     * @return
     */
    Result<IPage<ShortLinkPageRespDto>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDto requestParam);
}
