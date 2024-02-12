package com.huameng.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.common.convention.result.Results;
import com.huameng.shortlink.admin.dto.req.UserLoginReqDto;
import com.huameng.shortlink.admin.dto.req.UserRegisterReqDto;
import com.huameng.shortlink.admin.dto.req.UserUpdateDto;
import com.huameng.shortlink.admin.dto.resp.UserActualRespDto;
import com.huameng.shortlink.admin.dto.resp.UserLoginRespDto;
import com.huameng.shortlink.admin.dto.resp.UserRespDto;
import com.huameng.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     *
     * 根据用户名查询用户信息
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询真实用户信息
     * @param username
     * @return
     */
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDto> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDto.class));
    }

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username")String username){
        return Results.success(!userService.hasUsername(username));
    }

    /**
     * 用户注册
     * @param requestParam 用户注册入参
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDto requestParam){
        userService.register(requestParam);
        return Results.success();

    }

    /**
     * 修改用户
     * @param requestParam 入参
     * @return
     */
    @PutMapping("/api/short-link/v1/user")
    public Result<Void> update(@RequestBody UserUpdateDto requestParam){
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/user/login")
    public Result<UserLoginRespDto> login(@RequestBody UserLoginReqDto requestParam){
        return Results.success(userService.login(requestParam));
    }

    /**
     * 用户登录检查
     * @param token
     * @return
     */
    @GetMapping("/api/short-link/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username,@RequestParam("token") String token){
        return Results.success(userService.checkLogin(username, token));
    }

    @DeleteMapping ("/api/short-link/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username,@RequestParam("token")String token){
        userService.logout(username, token);
        return Results.success();
    }




}
