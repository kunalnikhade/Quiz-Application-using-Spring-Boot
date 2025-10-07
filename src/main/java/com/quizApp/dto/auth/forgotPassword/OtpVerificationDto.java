package com.quizApp.dto.auth.forgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class OtpVerificationDto
{
    @NotNull(message = "OTP is required")
    private Integer otp;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email is required")
    private String email;

    public OtpVerificationDto()
    {
    }

    public OtpVerificationDto(final Integer otp, final String email)
    {
        this.otp = otp;
        this.email = email;
    }

    public Integer getOtp()
    {
        return otp;
    }

    public void setOtp(final Integer otp)
    {
        this.otp = otp;
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