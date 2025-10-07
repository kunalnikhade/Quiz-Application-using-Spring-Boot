package com.quizApp.dto.auth.forgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OtpRequestDto
{
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public OtpRequestDto()
    {
    }

    public OtpRequestDto(final String email)
    {
        this.email = email;
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
