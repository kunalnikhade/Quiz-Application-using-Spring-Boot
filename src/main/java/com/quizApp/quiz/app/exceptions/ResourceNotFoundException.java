package com.quizApp.quiz.app.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(final String message)
    {
        super(message);
    }
}
