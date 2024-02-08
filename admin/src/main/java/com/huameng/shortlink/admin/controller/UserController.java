package com.huameng.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.common.convention.result.Results;
import com.huameng.shortlink.admin.dto.resp.UserActualRespDto;
import com.huameng.shortlink.admin.dto.resp.UserRespDto;
import com.huameng.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor //构造器注入
public class UserController {

    private final UserService userService;
    /**
     *
     * 根据用户名查询用户信息
     */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询真实用户信息
     * @param username
     * @return
     */
    @GetMapping("/api/shortlink/v1/actual/user/{username}")
    public Result<UserActualRespDto> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDto.class));
    }
}
