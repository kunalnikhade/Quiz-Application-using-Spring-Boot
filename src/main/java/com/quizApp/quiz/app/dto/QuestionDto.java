package com.quizApp.quiz.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto
{
    private String questionTitle;

    private String option1;

    private String option2;

    private String option3;

    private String option4;
}
