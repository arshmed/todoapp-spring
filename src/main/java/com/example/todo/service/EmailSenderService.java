package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final String subject = "WELCOME!";
    private final String text = "WELCOME TO TODO APP!\nYou can put your plans there and you will not forget anything!";

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("test@mail.com");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully..");

    }

}
