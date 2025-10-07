package com.quizApp.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionResponse
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

    public QuestionResponse()
    {
    }

    public QuestionResponse(final String questionTitle,
                            final String option1,
                            final String option2,
                            final String option3,
                            final String option4)
    {
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public String getQuestionTitle()
    {
        return questionTitle;
    }

    public void setQuestionTitle(final String questionTitle)
    {
        this.questionTitle = questionTitle;
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
}
