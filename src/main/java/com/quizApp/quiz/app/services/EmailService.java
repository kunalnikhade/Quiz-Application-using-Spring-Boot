package com.quizApp.quiz.app.services;

import com.quizApp.quiz.app.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(final MailBody mailBody)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailBody.to());
        message.setFrom("kunalnikhade.fy20@stvincentngp.edu.in");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        javaMailSender.send(message);
    }
}
