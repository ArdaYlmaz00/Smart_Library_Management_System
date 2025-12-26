package com.Library.Library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Her iki ismi de destekliyoruz ki hata vermesin
    public void sendSimpleMessage(String to, String subject, String text) {
        sendEmailInternal(to, subject, text);
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        sendEmailInternal(to, subject, text);
    }

    private void sendEmailInternal(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("Mail gönderildi: " + to);
        } catch (Exception e) {
            System.err.println("Mail hatası: " + e.getMessage());
        }
    }
}