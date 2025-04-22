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

    @PostMapping(value = "/addQuestion", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<QuestionDto> addQuestions(final @RequestBody QuestionDto questionDto)
    {
        return new ResponseEntity<>(questionService.addQuestions(questionDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/allQuestions", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<QuestionDto>> getAllQuestions()
    {
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping(value = "/questions/{category}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<QuestionDto>> getQuestionByCategory(final @PathVariable String category)
    {
        return new ResponseEntity<>(questionService.questionByCategory(category), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAllQuestions", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteAllQuestions()
    {
        questionService.deleteAllQuestions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/deleteQuestion/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteById(final @PathVariable Integer id)
    {
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
