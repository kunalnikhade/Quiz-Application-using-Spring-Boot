package com.quizApp.quiz.app.controllers;

import com.quizApp.quiz.app.model.UserEntity;
import com.quizApp.quiz.app.services.UserService;
import com.quizApp.quiz.app.utils.LoginRequest;
import com.quizApp.quiz.app.utils.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequest registerRequest)
    {
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            return new ResponseEntity<>(userService.verify(loginRequest), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
