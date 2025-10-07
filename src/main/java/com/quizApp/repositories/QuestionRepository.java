package com.quizApp.repositories;

import com.quizApp.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID>
{
    @Query("SELECT q FROM QuestionEntity q WHERE LOWER(q.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<QuestionEntity> findByCategory(final String category);


    @Query(
            value = "SELECT * FROM questions q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numOfQue",
            nativeQuery = true)
    List<QuestionEntity> findRandomQuestionsByCategory(final String category, final Integer numOfQue);
}
