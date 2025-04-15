package com.quizApp.quiz.app.services;

import com.quizApp.quiz.app.dto.QuestionDto;
import com.quizApp.quiz.app.dto.Response;
import com.quizApp.quiz.app.entities.QuestionEntity;
import com.quizApp.quiz.app.entities.QuizEntity;
import com.quizApp.quiz.app.exceptions.ResourceNotFoundException;
import com.quizApp.quiz.app.repositories.QuestionRepository;
import com.quizApp.quiz.app.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService
{
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizService(final QuizRepository quizRepository, final QuestionRepository questionRepository)
    {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void createQuiz(final String category, final String title, final Integer numOfQue)
    {
        List<QuestionEntity> questions = questionRepository.findRandomQuestionsByCategory(category, numOfQue);

        if (questions.isEmpty())
        {
            throw new ResourceNotFoundException("Questions are not found by this category : " +category);
        }

        QuizEntity quiz = new QuizEntity();

        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getQuizById(final Integer id)
    {
        Optional<QuizEntity> quiz = quizRepository.findById(id);

        if(quiz.isEmpty())
        {
            throw new ResourceNotFoundException("Not Found a Quiz : " +id);
        }

        List<QuestionEntity> questions = quiz.get().getQuestions();

        return questions.stream()
                .map(q -> new QuestionDto(
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer calculateResult(final Integer id, final List<Response> responses)
    {
        Optional<QuizEntity> quiz = quizRepository.findById(id);

        if(quiz.isEmpty())
        {
            throw new ResourceNotFoundException("Not Found a Quiz : " +id);
        }

        List<QuestionEntity> questions = quiz.get().getQuestions();

        int correct = 0; int i = 0;

        for(Response response: responses)
        {
            if(response.getResponse().equals(questions.get(i).getCorrectAnswer()))
            {
                correct++;
            }
            i++;
        }
        return correct;
    }
}
