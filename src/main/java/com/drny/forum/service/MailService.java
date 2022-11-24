package com.drny.forum.service;

import com.drny.forum.pojo.MailRequest;

public interface MailService {

    void sendMail(MailRequest mailRequest);

    void sendHtmlMail(MailRequest mailRequest);

}
