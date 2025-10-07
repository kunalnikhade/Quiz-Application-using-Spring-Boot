package com.quizApp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz")
public class QuizEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<QuestionEntity> questions;

    public QuizEntity()
    {
    }

    public QuizEntity(final Integer id, final String title, final List<QuestionEntity> questions)
    {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public List<QuestionEntity> getQuestions()
    {
        return questions;
    }

    public void setQuestions(final List<QuestionEntity> questions)
    {
        this.questions = questions;
    }
}