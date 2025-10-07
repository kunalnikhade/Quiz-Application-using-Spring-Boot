package com.quizApp.controllers.auth;

import com.quizApp.dto.auth.UserDto;
import com.quizApp.model.auth.UserEntity;
import com.quizApp.services.auth.AuthService;
import com.quizApp.dto.auth.LoginRequest;
import com.quizApp.dto.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController
{
    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping(
            value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequest registerRequest)
    {
        return new ResponseEntity<>(this.authService.register(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            return new ResponseEntity<>(this.authService.verify(loginRequest), HttpStatus.OK);
        }
        catch (final Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/current-user")
    public ResponseEntity<UserDto> getUser()
    {
        return new ResponseEntity<>(this.authService.getCurrentUser(), HttpStatus.OK);
    }
}
