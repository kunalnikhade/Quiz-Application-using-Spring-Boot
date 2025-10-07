package com.quizApp.services.auth;


import com.quizApp.dto.auth.ResetPasswordDto;
import com.quizApp.exceptions.ResourceNotFoundException;
import com.quizApp.model.auth.UserEntity;
import com.quizApp.repositories.auth.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(final UserRepository userRepository, final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void resetPassword(final ResetPasswordDto resetPasswordDto)
    {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();

        final UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword()))
        {
            throw new ResourceNotFoundException("Old password does not match");
        }

        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getRepeatPassword()))
        {
            throw new ResourceNotFoundException("New password does not match");
        }

        user.setPassword(this.passwordEncoder.encode(resetPasswordDto.getNewPassword()));

        this.userRepository.save(user);
    }
}
