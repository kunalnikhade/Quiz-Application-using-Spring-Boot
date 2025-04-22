package com.quizApp.quiz.app.services;

import com.quizApp.quiz.app.dto.QuestionDto;
import com.quizApp.quiz.app.model.QuestionEntity;
import com.quizApp.quiz.app.exceptions.ResourceNotFoundException;
import com.quizApp.quiz.app.repositories.QuestionRepository;
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
    public QuestionDto addQuestions(final QuestionDto questionDto)
    {
        QuestionEntity questionEntity = convertToEntity(questionDto);

        return convertToDto(questionRepository.save(questionEntity));
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getAllQuestions()
    {
        return questionRepository.findAll().stream()
                .map(questionEntity -> convertToDto(questionEntity))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> questionByCategory(final String category)
    {
        List<QuestionEntity> questionEntities = questionRepository.findByCategory(category);

        if (questionEntities.isEmpty())
        {
            throw new ResourceNotFoundException("Questions are not found by this category : " + category);
        }

        return questionEntities.stream()
                .map(questionEntity -> convertToDto(questionEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllQuestions()
    {
        questionRepository.deleteAll();
    }

    @Transactional
    public void deleteQuestionById(final Integer id)
    {
        QuestionEntity questionEntity =  questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question are not found by id : " + id));

        questionRepository.delete(questionEntity);
    }

    public QuestionDto convertToDto(final QuestionEntity questionEntity)
    {
        final QuestionDto questionDto = new QuestionDto();

        questionDto.setQuestionTitle(questionEntity.getQuestionTitle());
        questionDto.setOption1(questionEntity.getOption1());
        questionDto.setOption2(questionEntity.getOption2());
        questionDto.setOption3(questionEntity.getOption3());
        questionDto.setOption4(questionEntity.getOption4());

        return questionDto;
    }

    public QuestionEntity convertToEntity(final QuestionDto questionDto)
    {
        final QuestionEntity questionEntity = new QuestionEntity();

        questionEntity.setQuestionTitle(questionDto.getQuestionTitle());
        questionEntity.setOption1(questionDto.getOption1());
        questionEntity.setOption2(questionDto.getOption2());
        questionEntity.setOption3(questionDto.getOption3());
        questionEntity.setOption4(questionDto.getOption4());

        return questionEntity;
    }
}
