package com.quizApp.dto.auth;

import jakarta.validation.constraints.NotNull;

public class UserDto
{
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    private String email;

    public UserDto()
    {
    }

    public UserDto(final String name, final String email)
    {
        this.name = name;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }
}
