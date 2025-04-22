package com.quizApp.quiz.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto
{
    @NotBlank(message = "Add the Questions title")
    private String questionTitle;

    @NotBlank(message = "Add the Questions option1")
    private String option1;

    @NotBlank(message = "Add the Questions option2")
    private String option2;

    @NotBlank(message = "Add the Questions option3")
    private String option3;

    @NotBlank(message = "Add the Questions option4")
    private String option4;
}
