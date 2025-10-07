package com.quizApp.services.auth;

import com.quizApp.dto.auth.forgotPassword.ChangePasswordDto;
import com.quizApp.dto.auth.MailBody;
import com.quizApp.dto.auth.forgotPassword.OtpRequestDto;
import com.quizApp.dto.auth.forgotPassword.OtpVerificationDto;
import com.quizApp.exceptions.ResourceNotFoundException;
import com.quizApp.model.auth.ForgotPasswordEntity;
import com.quizApp.model.auth.UserEntity;
import com.quizApp.repositories.auth.ForgotPasswordRepository;
import com.quizApp.repositories.auth.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForgotPasswordService
{
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordService(final ForgotPasswordRepository forgotPasswordRepository,
                                 final UserRepository userRepository,
                                 final EmailService emailService,
                                 final PasswordEncoder passwordEncoder)
    {
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendVerificationOtp(final OtpRequestDto otpRequestDto)
    {
        final UserEntity user = this.userRepository.findByEmail(otpRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        final Integer generatedOtp = generateOtp();

        // send forgot password email to user
        final MailBody mailBody = new MailBody(
                user.getEmail(),
                "OTP to Forgot Password",
                """
                Hello,
        
                We received a request to forgot your password.
                Use the OTP below to continue:
        
                OTP: %s
        
                Do not share this OTP with anyone. 
                If you did not request a password forgot, please ignore this email.
        
                Thank you,
                SmartQuiz
                """.formatted(generatedOtp)
        );

        // Check if forgot password already exists for the user
        final ForgotPasswordEntity forgotPassword = this.forgotPasswordRepository.findByUser(user)
                .orElseGet(() -> {
                    ForgotPasswordEntity newEntity = new ForgotPasswordEntity();
                    newEntity.setUser(user);
                    return newEntity;
                });

        // update or create
        forgotPassword.setOtpCode(generatedOtp);
        forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));

        this.emailService.sendSimpleMessage(mailBody);

        this.forgotPasswordRepository.save(forgotPassword);
    }

    public void verifyOtp(final OtpVerificationDto otpVerificationDto)
    {
        final UserEntity user = this.userRepository.findByEmail(otpVerificationDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        final ForgotPasswordEntity forgotPassword = this.forgotPasswordRepository.findByOtpAndUser(otpVerificationDto.getOtp(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Forgot Password not found"));

        // Validate expiration time
        if (forgotPassword.getExpirationTime().before(new Date()))
        {
            throw new ResourceNotFoundException("OTP has expired");
        }

        // Invalidate OTP after use
        this.forgotPasswordRepository.delete(forgotPassword);
    }

    public void changePassword(final ChangePasswordDto changePasswordDto)
    {
        if (!Objects.equals(changePasswordDto.getPassword(), changePasswordDto.getRepeatPassword()))
        {
            throw new ResourceNotFoundException("Passwords do not match!");
        }

        // Check if user exists first
        this.userRepository.findByEmail(changePasswordDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + changePasswordDto.getEmail()));

        final String encryptedPassword = this.passwordEncoder.encode(changePasswordDto.getPassword());

        this.userRepository.updatePassword(changePasswordDto.getEmail(), encryptedPassword);
    }

    private Integer generateOtp()
    {
        final Random random = new Random();

        return random.nextInt(100_000, 999_999);
    }
}