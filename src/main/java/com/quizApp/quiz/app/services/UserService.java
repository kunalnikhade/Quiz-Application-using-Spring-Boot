package com.quizApp.quiz.app.services;

import com.quizApp.quiz.app.model.UserEntity;
import com.quizApp.quiz.app.model.UserRole;
import com.quizApp.quiz.app.repositories.UserRepository;
import com.quizApp.quiz.app.utils.LoginRequest;
import com.quizApp.quiz.app.utils.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder, final AuthenticationManager authenticationManager, final JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserEntity register(final RegisterRequest registerRequest)
    {
        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());

        UserEntity user = new UserEntity();

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    @Transactional
    public String verify(final LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (authentication.isAuthenticated())
        {
            return jwtService.generateToken(loginRequest.getEmail());
        }

        return "Invalid email or password";
    }
}
