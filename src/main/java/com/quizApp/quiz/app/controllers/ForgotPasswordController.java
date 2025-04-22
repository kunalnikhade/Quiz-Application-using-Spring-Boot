package com.quizApp.quiz.app.controllers;

import com.quizApp.quiz.app.dto.MailBody;
import com.quizApp.quiz.app.exceptions.ResourceNotFoundException;
import com.quizApp.quiz.app.model.ForgotPasswordEntity;
import com.quizApp.quiz.app.model.UserEntity;
import com.quizApp.quiz.app.repositories.ForgotPasswordRepository;
import com.quizApp.quiz.app.repositories.UserRepository;
import com.quizApp.quiz.app.services.EmailService;
import com.quizApp.quiz.app.utils.ChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping(value = "/api/forgotPassword")
public class ForgotPasswordController
{
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordController(final UserRepository userRepository, final EmailService emailService, final ForgotPasswordRepository forgotPasswordRepository, final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/verifyEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable final String email)
    {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Integer otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .subject("OTP for Forgot Password Verification")
                .text("This is a email verification for forgot password is : " + otp + "\n Do not share it with Anyone !!")
                .build();

        // Check if forgot password already exists for the user
        ForgotPasswordEntity forgotPassword = forgotPasswordRepository.findByUser(user)
                .orElse(ForgotPasswordEntity.builder().user(user).build());

        // update or create
        forgotPassword.setOtpCode(otp);
        forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        return new ResponseEntity<>("Verification email sent successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/verifyOtp/{otpCode}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable final Integer otpCode, @PathVariable final String email)
    {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + email));

        forgotPasswordRepository.findByOtpAndUser(otpCode, user)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or Expired Otp : " + otpCode));

        return new ResponseEntity<>("OTP verified !!", HttpStatus.OK);
    }

    @PostMapping(value = "/changePassword/{email}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> changePassword(@RequestBody final ChangePassword changePassword, @PathVariable final String email)
    {
        if(!Objects.equals(changePassword.getPassword(), changePassword.getRepeatPassword()))
        {
            return new ResponseEntity<>("Password do not match !!", HttpStatus.NOT_ACCEPTABLE);
        }

        String encryptedPassword = passwordEncoder.encode(changePassword.getPassword());

        userRepository.updatePassword(email, encryptedPassword);

        return new ResponseEntity<>("Password changed successfully !!", HttpStatus.OK);
    }

    private Integer otpGenerator()
    {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
