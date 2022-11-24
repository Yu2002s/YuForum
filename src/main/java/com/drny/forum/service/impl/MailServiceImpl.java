package com.drny.forum.service.impl;

import com.drny.forum.pojo.MailRequest;
import com.drny.forum.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Override
    public void sendMail(MailRequest mailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sendMailer);
        simpleMailMessage.setSubject(mailRequest.getSubject());
        simpleMailMessage.setTo(mailRequest.getSendTo());
        simpleMailMessage.setText(mailRequest.getText());
        simpleMailMessage.setSentDate(new Date());

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendHtmlMail(MailRequest mailRequest) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String filePath = mailRequest.getFilePath();
            MimeMessageHelper helper = new MimeMessageHelper(message, !filePath.isBlank());
            helper.setFrom(sendMailer);
            helper.setSubject(mailRequest.getSubject());
            helper.setTo(mailRequest.getSendTo());
            helper.setText(mailRequest.getText(), true);
            helper.setSentDate(new Date());
            FileSystemResource resource = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
