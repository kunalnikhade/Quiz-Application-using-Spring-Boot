package com.quizApp.model.auth;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "forgotPassword")
public class ForgotPasswordEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forgotPasswordId")
    private Integer forgotPasswordId;

    @Column(
            name = "otpCode",
            nullable = false)
    private Integer otpCode;

    @Column(
            name = "expirationTime",
            nullable = false)
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public ForgotPasswordEntity()
    {
    }

    public ForgotPasswordEntity(final Integer forgotPasswordId, final Integer otpCode, final Date expirationTime, final UserEntity user)
    {
        this.forgotPasswordId = forgotPasswordId;
        this.otpCode = otpCode;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    public Integer getForgotPasswordId()
    {
        return forgotPasswordId;
    }

    public void setForgotPasswordId(final Integer forgotPasswordId)
    {
        this.forgotPasswordId = forgotPasswordId;
    }

    public Integer getOtpCode()
    {
        return otpCode;
    }

    public void setOtpCode(final Integer otpCode)
    {
        this.otpCode = otpCode;
    }

    public Date getExpirationTime()
    {
        return expirationTime;
    }

    public void setExpirationTime(final Date expirationTime)
    {
        this.expirationTime = expirationTime;
    }

    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(final UserEntity user)
    {
        this.user = user;
    }
}
