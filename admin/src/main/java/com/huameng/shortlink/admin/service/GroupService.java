package com.huameng.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.admin.dao.entity.GroupDO;
import com.huameng.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDto;
import com.huameng.shortlink.admin.dto.resp.ShortLinkGroupRespDto;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     * @param groupName
     */
    void saveGroup(String groupName);

    /**
     * 新增短链接分组
     * @param username
     * @param groupName
     */
    void saveGroup(String username, String groupName);

    /**
     * 查询短链接分组
     */
    List<ShortLinkGroupRespDto> listGroup();

    /**
     * 修改短链接分组名称
     */
    void updateGroup(ShortLinkGroupUpdateReqDto requestParam);

    /**
     * 删除短链接分组
     */
    void deleteGroup(String gid);

}
