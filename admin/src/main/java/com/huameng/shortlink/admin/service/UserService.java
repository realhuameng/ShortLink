package com.huameng.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.admin.dao.entity.UserDO;
import com.huameng.shortlink.admin.dto.resp.UserRespDto;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户实体
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDto getUserByUsername(String username);
}
