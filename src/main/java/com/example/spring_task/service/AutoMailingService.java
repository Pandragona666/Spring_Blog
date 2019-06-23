package com.example.spring_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class AutoMailingService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMessage(String to, String subject, String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        //wysyłanie wiadooci z maila skonfigurowaneg w application.properties
        javaMailSender.send(simpleMailMessage);
    }
}
