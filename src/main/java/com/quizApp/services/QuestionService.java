package com.quizApp.services;

import com.quizApp.dto.QuestionRequest;
import com.quizApp.dto.QuestionResponse;
import com.quizApp.exceptions.ResourceNotFoundException;
import com.quizApp.model.QuestionEntity;
import com.quizApp.model.QuizEntity;
import com.quizApp.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService
{
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(final QuestionRepository questionRepository)
    {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public QuestionResponse addQuestions(final QuestionRequest questionRequest)
    {
        final QuestionEntity questionEntity = convertToEntity(questionRequest);

        return convertToDto(this.questionRepository.save(questionEntity));
    }

    @Transactional
    public QuestionResponse updateQuestionUid(final QuestionRequest questionRequest, final Integer id)
    {
        final QuestionEntity question = this.questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        question.setCategory(questionRequest.getCategory());
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        question.setDifficultyLevel(questionRequest.getDifficultyLevel());
        question.setQuestionTitle(questionRequest.getQuestionTitle());
        question.setOption1(questionRequest.getOption1());
        question.setOption2(questionRequest.getOption2());
        question.setOption3(questionRequest.getOption3());
        question.setOption4(questionRequest.getOption4());

        return convertToDto(this.questionRepository.save(question));
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions()
    {
        return questionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> questionByCategory(final String category)
    {
        final List<QuestionEntity> questionEntities = this.questionRepository.findByCategory(category);

        if (questionEntities.isEmpty())
        {
            throw new ResourceNotFoundException("Questions are not found by this category : " + category);
        }

        return questionEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllQuestions()
    {
        this.questionRepository.deleteAll();
    }

    @Transactional
    public void deleteQuestionById(final Integer id)
    {
        final QuestionEntity question = this.questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question are not found by id : " + id));

        for (QuizEntity quiz : question.getQuizzes())
        {
            quiz.getQuestions().remove(question);
        }

        question.getQuizzes().clear();

        this.questionRepository.delete(question);
    }

    public QuestionResponse convertToDto(final QuestionEntity questionEntity)
    {
        final QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setQuestionTitle(questionEntity.getQuestionTitle());
        questionResponse.setOption1(questionEntity.getOption1());
        questionResponse.setOption2(questionEntity.getOption2());
        questionResponse.setOption3(questionEntity.getOption3());
        questionResponse.setOption4(questionEntity.getOption4());

        return questionResponse;
    }

    public QuestionEntity convertToEntity(final QuestionRequest questionRequest)
    {
        final QuestionEntity questionEntity = new QuestionEntity();

        questionEntity.setQuestionTitle(questionRequest.getQuestionTitle());
        questionEntity.setCategory(questionRequest.getCategory());
        questionEntity.setDifficultyLevel(questionRequest.getDifficultyLevel());
        questionEntity.setCorrectAnswer(questionRequest.getCorrectAnswer());
        questionEntity.setOption1(questionRequest.getOption1());
        questionEntity.setOption2(questionRequest.getOption2());
        questionEntity.setOption3(questionRequest.getOption3());
        questionEntity.setOption4(questionRequest.getOption4());

        return questionEntity;
    }
}
