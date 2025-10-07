package com.quizApp.controllers.auth;

import com.quizApp.dto.auth.ResetPasswordDto;
import com.quizApp.services.auth.ResetPasswordService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/reset")
public class ResetPasswordController
{
    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(final ResetPasswordService resetPasswordService)
    {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping(value = "/change-password",
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> resetPassword(@RequestBody final ResetPasswordDto resetPasswordDto)
    {
        this.resetPasswordService.resetPassword(resetPasswordDto);

        return new ResponseEntity<>("Reset password successfully", HttpStatus.OK);
    }
}