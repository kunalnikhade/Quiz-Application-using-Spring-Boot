package com.quizApp.quiz.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "quiz")
public class QuizEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "quiz_questions", // name of the join table
            joinColumns = @JoinColumn(name = "quiz_id"), // FK referencing QuizEntity
            inverseJoinColumns = @JoinColumn(name = "question_id") // FK referencing QuestionEntity
    )
    private List<QuestionEntity> questions;
}
