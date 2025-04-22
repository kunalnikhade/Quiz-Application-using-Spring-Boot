package com.quizApp.quiz.app.repositories;

import com.quizApp.quiz.app.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer>
{
    List<QuestionEntity> findByCategory(final String category);

    @Query(value = "SELECT * FROM questions q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numOfQue", nativeQuery = true)
    List<QuestionEntity> findRandomQuestionsByCategory(final String category, final Integer numOfQue);

}
