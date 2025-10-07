package com.quizApp.dto.auth;

import jakarta.validation.constraints.NotNull;

public class ResetPasswordDto
{
    @NotNull(message = "Old password is required")
    private String oldPassword;

    @NotNull(message = "New password is required")
    private String newPassword;

    @NotNull(message = "Repeat password is required")
    private String repeatPassword;

    public ResetPasswordDto()
    {
    }

    public ResetPasswordDto(final String oldPassword, final String newPassword, final String repeatPassword)
    {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(final String newPassword)
    {
        this.newPassword = newPassword;
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
