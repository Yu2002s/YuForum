package com.drny.forum.controller;

import com.drny.forum.exception.BusinessException;
import com.drny.forum.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result<Object> doException(Exception exception) {
        return new Result<>("warning", "服务器内部出现错误，请重试" + exception.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result<Object> doSystemException(SystemException systemException) {
        return new Result<>(systemException.getStatus(), systemException.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Object> doBusinessException(BusinessException businessException) {
        return new Result<>(businessException.getStatus(), businessException.getMessage());
    }

}
