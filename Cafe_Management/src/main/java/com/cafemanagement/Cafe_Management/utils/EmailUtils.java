package com.cafemanagement.Cafe_Management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender javaMailSender;

    public void forgetMail(String to , String subject, String password) throws MessagingException {
        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("shwetshukla0710@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMSG = "<p><b>Your Login details for Cafe Management System</b></p><b>Email:</b>"+ to + "<br><b>Password: </b>" + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMSG , "text/html");
        javaMailSender.send(message);
    }

}
