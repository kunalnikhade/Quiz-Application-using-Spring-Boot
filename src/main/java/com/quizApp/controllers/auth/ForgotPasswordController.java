package com.quizApp.controllers.auth;

import com.quizApp.dto.auth.forgotPassword.ChangePasswordDto;
import com.quizApp.dto.auth.forgotPassword.OtpRequestDto;
import com.quizApp.dto.auth.forgotPassword.OtpVerificationDto;
import com.quizApp.services.auth.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/forgot-password")
public class ForgotPasswordController
{
    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(final ForgotPasswordService forgotPasswordService)
    {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping(value = "/request-otp")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid final OtpRequestDto otpRequestDto)
    {
        this.forgotPasswordService.sendVerificationOtp(otpRequestDto);

        return new ResponseEntity<>("Verification email sent successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid final OtpVerificationDto otpVerificationDto)
    {
        this.forgotPasswordService.verifyOtp(otpVerificationDto);

        return new ResponseEntity<>("OTP verified", HttpStatus.OK);
    }

    @PostMapping(value = "/change-password",
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> changePassword(@RequestBody final ChangePasswordDto changePasswordDto)
    {
        this.forgotPasswordService.changePassword(changePasswordDto);

        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}
