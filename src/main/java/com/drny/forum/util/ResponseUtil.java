package com.drny.forum.util;

import com.drny.forum.controller.Result;

public class ResponseUtil {

    public static Result<Void> error(String error) {
        return new Result<>("error", null, error);
    }

    public static Result<Void> success(String success) {
        return new Result<>("ok", null, success);
    }

    public static <T> Result<T> errorBy(String error) {
        return new Result<>("error", null, error);
    }

    public static <T> Result<T> success(T success) {
        return new Result<>("ok",  success, null);
    }

}
