package com.quizApp.dto.auth.forgotPassword;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordDto
{
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Repeat password is required")
    private String repeatPassword;

    public ChangePasswordDto()
    {
    }

    public ChangePasswordDto(final String email, final String password, final String repeatPassword)
    {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public String getRepeatPassword()
    {
        return repeatPassword;
    }

    public void setRepeatPassword(final String repeatPassword)
    {
        this.repeatPassword = repeatPassword;
    }
}
