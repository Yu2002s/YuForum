package com.drny.forum.exception;

public class SystemException extends RuntimeException {

    private String status;

    public SystemException(String status, String message) {
        super(message);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
