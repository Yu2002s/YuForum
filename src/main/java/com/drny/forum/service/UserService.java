package com.drny.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drny.forum.controller.Result;
import com.drny.forum.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService  {

    Result<User> login(User user, HttpServletRequest request, HttpServletResponse response);

    Result<Void> sign(User user, String verifyCode);

    Result<Void> sendVerifyCode(String email);

}
