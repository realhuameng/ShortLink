package com.huameng.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import com.huameng.shortlink.project.dto.req.RecycleBinSaveReqDto;

/**
 * 回收站管理接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDto requestParam);
}
