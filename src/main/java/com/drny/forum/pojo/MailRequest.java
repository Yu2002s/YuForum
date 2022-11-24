package com.drny.forum.pojo;

import lombok.Data;

@Data
public class MailRequest {

    private String sendTo;
    private String subject;
    private String text;
    private String filePath;
}
