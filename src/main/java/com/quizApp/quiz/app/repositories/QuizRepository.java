package com.quizApp.quiz.app.repositories;

import com.quizApp.quiz.app.model.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer>
{

}
