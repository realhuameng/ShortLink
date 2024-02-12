package com.huameng.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.admin.dao.entity.GroupDO;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupName
     */
    void saveGroup(String groupName);
}
