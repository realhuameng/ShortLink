package com.huameng.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huameng.shortlink.admin.dao.entity.UserDO;
import com.huameng.shortlink.admin.dto.req.UserRegisterReqDto;
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

    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return 存在返回 true，不存在返回false
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     * @param registerParam 用户注册请求参数
     */
    void register(UserRegisterReqDto registerParam);
}
