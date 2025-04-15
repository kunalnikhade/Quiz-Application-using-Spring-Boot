package com.quizApp.quiz.app.controllers;

import com.quizApp.quiz.app.dto.QuestionDto;
import com.quizApp.quiz.app.dto.Response;
import com.quizApp.quiz.app.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createQuiz(final @RequestParam String category, final @RequestParam String title, final @RequestParam Integer numOfQue)
    {
       quizService.createQuiz(category, title, numOfQue);
       return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping(value = "/getQuiz/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getQuiz(@PathVariable final Integer id)
    {
        return new ResponseEntity<>(quizService.getQuizById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/submitQuiz/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> submitQuiz(@PathVariable final Integer id, final @RequestBody List<Response> responses)
    {
        return new ResponseEntity<>(quizService.calculateResult(id, responses), HttpStatus.OK);
    }
}