package com.drny.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.drny.forum.config.WebConfig;
import com.drny.forum.controller.Result;
import com.drny.forum.mapper.UserMapper;
import com.drny.forum.pojo.MailRequest;
import com.drny.forum.pojo.User;
import com.drny.forum.service.MailService;
import com.drny.forum.service.UserService;
import com.drny.forum.util.AesUtil;
import com.drny.forum.util.CookieUtil;
import com.drny.forum.util.JsonUtils;
import com.drny.forum.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<User> login(User user, HttpServletRequest request, HttpServletResponse response) {
        String userCookie = CookieUtil.getUserCookie(request);
        Result<User> result = new Result<>();
        User selectUser = null;
        // 判断是否携带用户信息
        if (userCookie != null) {
            // 如果携带了用户信息
            String json = AesUtil.decrypt(userCookie);
            if (json != null) {
                selectUser = JsonUtils.getObjetFormString(json, User.class);
            }
        } else {
            // 用户使用用户和密码进行登录
            selectUser = user;
        }

        if (selectUser != null) {
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUsername, selectUser.getUsername())
                    .eq(User::getPassword, selectUser.getPassword());
            User queryUser = userMapper.selectOne(lqw);
            if (queryUser != null) {
                // 用户登录成功了
                result.setStatus("ok");
                result.setMsg("登录成功");
                result.setData(queryUser);

                if (userCookie == null) {
                    CookieUtil.setUserCookie(queryUser, response);
                }
            } else {
                result.setStatus("login failure");
                result.setMsg("用户名或密码错误");
            }
        } else {
            result.setStatus("error");
            result.setMsg("用户信息获取失败");
        }

        return result;
    }

    @Transactional
    @Override
    public Result<Void> sign(User user, String verifyCode) {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        if (username == null || password == null || email == null
                || "".equals(username) || "".equals(password) || "".equals(email)) {
            return ResponseUtil.error("用户名,密码,邮箱不能为空");
        }

        username = username.trim();
        password = password.trim();
        email = email.trim();

        int usernameLen = username.length();
        int passwordLen = password.length();
        //int emailLen = email.length();
        if (usernameLen < 1 || passwordLen > 12 || passwordLen < 5) {
            return ResponseUtil.error("用户名或密码长度不规范");
        }

        verifyCode = verifyCode.trim();

        try {
            // 进行验证码判断
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String code = valueOperations.get(email);
            if (code == null || "".equals(verifyCode) || !code.equals(verifyCode)) {
                return ResponseUtil.error("验证码错误");
            }

            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUsername, username).or().eq(User::getEmail, email);
            User queryUser = userMapper.selectOne(lqw);
            if (queryUser != null) {
                return ResponseUtil.error("用户已存在");
            }

            user.setIcon(WebConfig.ICONS[new Random().nextInt(WebConfig.ICONS.length)]);

            if (userMapper.insert(user) < 1) {
                return ResponseUtil.error("用户注册失败了");
            }

            return ResponseUtil.success("注册成功");
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Void> sendVerifyCode(String email) {
        email = email.trim();
        if ("".equals(email)) {
            return ResponseUtil.error("邮箱不能为空");
        }

        try {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
                return ResponseUtil.error("验证码已发出");
            }
            int code = (int) ((Math.random() * 9 + 1) * 1000);

            redisTemplate.opsForValue().set(email, "" + code, Duration.ofMinutes(1));

            MailRequest mailRequest = new MailRequest();
            mailRequest.setSendTo(email);
            mailRequest.setSubject("注册验证");
            mailRequest.setText("你的验证码是: " + code + " (验证码一分钟内有效)");
            mailService.sendMail(mailRequest);

            return ResponseUtil.success("验证码已发出，请注意邮箱接收");
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage());
        }

    }




}
