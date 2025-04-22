package com.quizApp.quiz.app.dto;


import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text)
{

}
