package com.quizApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class QuestionRequest
{
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;

    @NotBlank(message = "Question title is required")
    private String questionTitle;

    @NotBlank(message = "Difficulty level is required")
    @Pattern(regexp = "^(Easy|Medium|Hard)$", message = "Difficulty level must be Easy, Medium, or Hard")
    private String difficultyLevel;

    @NotBlank(message = "Option 1 is required")
    private String option1;

    @NotBlank(message = "Option 2 is required")
    private String option2;

    @NotBlank(message = "Option 3 is required")
    private String option3;

    @NotBlank(message = "Option 4 is required")
    private String option4;

    @NotBlank(message = "Correct answer is required")
    @Pattern(regexp = "^(option1|option2|option3|option4)$",
             message = "Correct answer must be one of: option1, option2, option3, or option4")
    private String correctAnswer;

    public QuestionRequest()
    {
    }

    public QuestionRequest(final String category, final String questionTitle, final String difficultyLevel, final String option1, final String option2, final String option3, final String option4, final String correctAnswer)
    {
        this.category = category;
        this.questionTitle = questionTitle;
        this.difficultyLevel = difficultyLevel;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(final String category)
    {
        this.category = category;
    }

    public String getQuestionTitle()
    {
        return questionTitle;
    }

    public void setQuestionTitle(final String questionTitle)
    {
        this.questionTitle = questionTitle;
    }

    public String getDifficultyLevel()
    {
        return difficultyLevel;
    }

    public void setDifficultyLevel(final String difficultyLevel)
    {
        this.difficultyLevel = difficultyLevel;
    }

    public String getOption1()
    {
        return option1;
    }

    public void setOption1(final String option1)
    {
        this.option1 = option1;
    }

    public String getOption2()
    {
        return option2;
    }

    public void setOption2(final String option2)
    {
        this.option2 = option2;
    }

    public String getOption3()
    {
        return option3;
    }

    public void setOption3(final String option3)
    {
        this.option3 = option3;
    }

    public String getOption4()
    {
        return option4;
    }

    public void setOption4(final String option4)
    {
        this.option4 = option4;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer(final String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }
}
