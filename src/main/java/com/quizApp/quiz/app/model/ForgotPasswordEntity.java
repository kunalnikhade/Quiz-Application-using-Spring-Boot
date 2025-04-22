package com.quizApp.quiz.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "forgotPassword")
public class ForgotPasswordEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forgotPasswordId")
    private Integer forgotPasswordId;

    @Column(name = "otpCode", nullable = false)
    private Integer otpCode;

    @Column(name = "expirationTime", nullable = false)
    private Date expirationTime;

    @OneToOne
    private UserEntity user;
}
