package com.quizApp.controllers;

import com.quizApp.dto.QuestionResponse;
import com.quizApp.services.QuizService;
import com.quizApp.dto.QuizSubmitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController
{
    private final QuizService quizService;

    @Autowired
    public QuizController(final QuizService quizService)
    {
        this.quizService = quizService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            value = "/admin/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> createQuiz(final @RequestParam String category, final @RequestParam String title, final @RequestParam Integer numOfQue)
    {
        this.quizService.createQuiz(category, title, numOfQue);

        return new ResponseEntity<>("Quiz is Created !", HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<QuestionResponse>> getQuiz(@PathVariable final Integer id)
    {
        return new ResponseEntity<>(this.quizService.getQuizById(id), HttpStatus.OK);
    }

    @PostMapping(
            value = "/submit/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> submitQuiz(@PathVariable final Integer id, @RequestBody final List<QuizSubmitRequest> quizSubmitRequests)
    {
        return new ResponseEntity<>(this.quizService.calculateResult(id, quizSubmitRequests), HttpStatus.OK);
    }
}