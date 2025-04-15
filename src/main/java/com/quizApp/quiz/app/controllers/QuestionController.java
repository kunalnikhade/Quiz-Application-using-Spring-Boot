package com.quizApp.quiz.app.controllers;

import com.quizApp.quiz.app.dto.QuestionDto;
import com.quizApp.quiz.app.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController
{
    private final QuestionService questionService;

    @Autowired
    public QuestionController(final QuestionService questionService)
    {
        this.questionService = questionService;
    }

    @PostMapping(value = "/addQuestion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionDto addQuestions(final @RequestBody QuestionDto questionDto)
    {
        return questionService.addQuestions(questionDto);
    }

    @GetMapping(value = "/allQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionDto> getAllQuestions()
    {
        return questionService.getAllQuestions();
    }

    @GetMapping(value = "/questions/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionDto> getQuestionByCategory(final @PathVariable String category)
    {
        return questionService.questionByCategory(category);
    }

    @DeleteMapping(value = "/deleteAllQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAllQuestions()
    {
        questionService.deleteAllQuestions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/deleteQuestion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(final @PathVariable Integer id)
    {
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
