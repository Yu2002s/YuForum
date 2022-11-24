package com.drny.forum.controller;

import com.drny.forum.pojo.User;
import com.drny.forum.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping
    public Result<User> login(User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(user, request, response);
    }

    /**
     * 注册用户
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping
    public Result<Void> sign(User user, @RequestParam("code") String verifyCode) {
        return userService.sign(user, verifyCode);
    }

    @GetMapping("/verify/{email}")
    public Result<Void> sendVerifyCode(@PathVariable String email) {
        return userService.sendVerifyCode(email);
    }

}