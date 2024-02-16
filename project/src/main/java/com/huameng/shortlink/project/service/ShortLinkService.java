package com.huameng.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;

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

}
