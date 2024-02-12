package com.huameng.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 用户登录接口返回响应
 */
@Data
public class UserLoginRespDto {
    public UserLoginRespDto(String token){
        this.token = token;
    }
    /**
     * 用户token
     */
    private String token;
}
