package com.quizApp.services;

import com.quizApp.dto.QuestionResponse;
import com.quizApp.exceptions.ResourceNotFoundException;
import com.quizApp.model.QuestionEntity;
import com.quizApp.model.QuizEntity;
import com.quizApp.repositories.QuestionRepository;
import com.quizApp.repositories.QuizRepository;
import com.quizApp.dto.QuizSubmitRequest;
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
        final List<QuestionEntity> questions = this.questionRepository.findRandomQuestionsByCategory(category, numOfQue);

        if (questions.isEmpty())
        {
            throw new ResourceNotFoundException("Questions are not found by this category : " + category);
        }

        QuizEntity quiz = new QuizEntity();

        quiz.setTitle(title);
        quiz.setQuestions(questions);

        this.quizRepository.save(quiz);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuizById(final Integer id)
    {
        final Optional<QuizEntity> quiz = this.quizRepository.findById(id);

        if (quiz.isEmpty())
        {
            throw new ResourceNotFoundException("Not Found a Quiz : " + id);
        }

        final List<QuestionEntity> questions = quiz.get().getQuestions();

        return questions.stream()
                .map(q -> new QuestionResponse(
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String calculateResult(final Integer id, final List<QuizSubmitRequest> quizSubmitRequests)
    {
        final Optional<QuizEntity> quiz = this.quizRepository.findById(id);

        if (quiz.isEmpty())
        {
            throw new ResourceNotFoundException("Not Found a Quiz : " + id);
        }

        final List<QuestionEntity> questions = quiz.get().getQuestions();

        int correct = 0;
        int i = 0;

        for (QuizSubmitRequest quizSubmitRequest : quizSubmitRequests)
        {
            if (quizSubmitRequest.getResponse().equals(questions.get(i).getCorrectAnswer()))
            {
                correct++;
            }
            i++;
        }
        return "Total Score = " + correct;
    }
}
