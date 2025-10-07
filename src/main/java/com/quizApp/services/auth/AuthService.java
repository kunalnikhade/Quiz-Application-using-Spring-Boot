package com.quizApp.services.auth;

import com.quizApp.dto.auth.UserDto;
import com.quizApp.model.auth.UserEntity;
import com.quizApp.enumeration.UserRole;
import com.quizApp.repositories.auth.UserRepository;
import com.quizApp.dto.auth.LoginRequest;
import com.quizApp.dto.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(final UserRepository userRepository, final PasswordEncoder passwordEncoder, final AuthenticationManager authenticationManager, final JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserEntity register(final RegisterRequest registerRequest)
    {
        String encodePassword = this.passwordEncoder.encode(registerRequest.getPassword());

        UserEntity user = new UserEntity();

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword);
        user.setRole(UserRole.USER);

        return this.userRepository.save(user);
    }

    @Transactional
    public String verify(final LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (authentication.isAuthenticated())
        {
            return this.jwtService.generateToken(loginRequest.getEmail());
        }

        return "Invalid email or password";
    }

    public UserDto getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        final UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(user.getName(), user.getEmail());
    }
}
