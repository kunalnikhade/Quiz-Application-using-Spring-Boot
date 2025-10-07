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
    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<QuestionResponse> updateQuestionById(final @RequestBody QuestionRequest questionRequest, @PathVariable final Integer id)
    {
        return new ResponseEntity<>(this.questionService.updateQuestionUid(questionRequest, id), HttpStatus.OK);
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
            value = "/admin/delete/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteById(final @PathVariable Integer id)
    {
        this.questionService.deleteQuestionById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
