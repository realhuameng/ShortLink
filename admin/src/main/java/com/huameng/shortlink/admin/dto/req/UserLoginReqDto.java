package com.huameng.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户登录请求参数
 */
@Data
public class UserLoginReqDto {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
