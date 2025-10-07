package com.quizApp.controllers;

import com.quizApp.dto.QuestionRequest;
import com.quizApp.dto.QuestionResponse;
import com.quizApp.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/question")
public class QuestionController
{
    private final QuestionService questionService;

    @Autowired
    public QuestionController(final QuestionService questionService)
    {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            value = "/admin/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<QuestionResponse> addQuestions(final @RequestBody QuestionRequest questionRequest)
    {
        return new ResponseEntity<>(this.questionService.addQuestions(questionRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/update/{Uid}")
    public ResponseEntity<QuestionResponse> updateQuestionByUid(final @RequestBody QuestionRequest questionRequest, @PathVariable final UUID Uid)
    {
        return new ResponseEntity<>(this.questionService.updateQuestionUid(questionRequest, Uid), HttpStatus.OK);
    }

    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<QuestionResponse>> getAllQuestions()
    {
        return new ResponseEntity<>(this.questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping(
            value = "/{category}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<QuestionResponse>> getQuestionByCategory(@PathVariable final String category)
    {
        return new ResponseEntity<>(this.questionService.questionByCategory(category), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(
            value = "/admin/deleteAll",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteAllQuestions()
    {
        this.questionService.deleteAllQuestions();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(
            value = "/admin/delete/{Uid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteByUid(final @PathVariable UUID Uid)
    {
        this.questionService.deleteQuestionById(Uid);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
