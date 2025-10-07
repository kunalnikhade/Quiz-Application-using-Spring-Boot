package com.quizApp.dto;

import jakarta.validation.constraints.NotNull;

public class QuizSubmitRequest
{
    @NotNull(message = "ID is required")
    private Integer id;

    @NotNull(message = "Response is required")
    private String response;

    public QuizSubmitRequest()
    {
    }

    public QuizSubmitRequest(final Integer id, final String response)
    {
        this.id = id;
        this.response = response;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(final String response)
    {
        this.response = response;
    }
}
