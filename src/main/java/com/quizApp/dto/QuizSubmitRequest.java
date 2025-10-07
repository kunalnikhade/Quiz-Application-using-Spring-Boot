package com.quizApp.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class QuizSubmitRequest
{
    @NotNull(message = "ID is required")
    private UUID id;

    @NotNull(message = "Response is required")
    private String response;

    public QuizSubmitRequest()
    {
    }

    public QuizSubmitRequest(final UUID id, final String response)
    {
        this.id = id;
        this.response = response;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(final UUID id)
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
