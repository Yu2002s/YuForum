package com.drny.forum.util;

import com.drny.forum.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 得到用户的Cookie
     *
     * @param request 请求体
     * @return 用户的身份信息
     */
    @Nullable
    public static String getUserCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        String userCookie = null;
        for (Cookie cookie : cookies) {
            if ("user".equals(cookie.getName())) {
                userCookie = cookie.getValue();
            }
        }
        return userCookie;
    }

    /**
     * Cookie 附带id，username，password信息
     *
     * @param user     登录用户
     * @param response 结果头
     */
    public static void setUserCookie(User user, HttpServletResponse response) {
        String setCookie = AesUtil.encrypt(JsonUtils.objectToString(user));
        response.addCookie(new Cookie("user", setCookie));
    }

    @Nullable
    public static User getUser(HttpServletRequest request) {
        String cookie = CookieUtil.getUserCookie(request);

        if (cookie == null || cookie.isEmpty()) {
            return null;
        }

        cookie = AesUtil.decrypt(cookie);
        return JsonUtils.fromString(cookie, User.class);
    }

}
