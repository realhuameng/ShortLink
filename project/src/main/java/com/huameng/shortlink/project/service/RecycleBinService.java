package com.huameng.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dto.req.RecycleBinSaveReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkPageRespDto;

/**
 * 回收站管理接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDto requestParam);

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDto> pageShortLink(ShortLinkRecycleBinPageReqDto requestParam);
}
