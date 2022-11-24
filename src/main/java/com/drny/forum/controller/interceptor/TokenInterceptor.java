package com.drny.forum.controller.interceptor;

import com.drny.forum.controller.Password;
import com.drny.forum.controller.Result;
import com.drny.forum.util.AesUtil;
import com.drny.forum.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    /**
     *  单次请求最大超时时间
     */
    private static final int MAX_TIME_OUT = 30000;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader("Token");
        // 获取请求的Token
        if (token == null) {
            // Token为空则显示错误
            writeError(response, "请求异常，请重试");
            return false;
        }

        // 解密Token
        String time = AesUtil.decrypt(token);
        if (time == null) {
            // 无法解密则显示错误
            writeError(response, "警告，非法请求");
            return false;
        }
        long l = Long.parseLong(time);
        // 当前时间距离本次请求时间是否超时，防止多次重复请求
        if (System.currentTimeMillis() - l > MAX_TIME_OUT) {
            writeError(response, "请求已超时，请重试");
            return false;
        }
        return true;
    }

    private void writeError(HttpServletResponse response, String message) throws IOException {
        Result<Object> result = new Result<>("error",  message);
        String json = JsonUtils.objectToString(result);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

}
