package com.quizApp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class QuestionEntity
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "category")
    private String category;

    @Column(name = "questionTitle")
    private String questionTitle;

    @Column(name = "difficultyLevel")
    private String difficultyLevel;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @Column(name = "option4")
    private String option4;

    @Column(name = "correctAnswer")
    private String correctAnswer;

    @ManyToMany(mappedBy = "questions", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuizEntity> quizzes;

    public QuestionEntity()
    {
    }

    public QuestionEntity(final UUID id, final String category, final String questionTitle, final String difficultyLevel, final String option1, final String option2, final String option3, final String option4, final String correctAnswer, final List<QuizEntity> quizzes)
    {
        this.id = id;
        this.category = category;
        this.questionTitle = questionTitle;
        this.difficultyLevel = difficultyLevel;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.quizzes = quizzes;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(final UUID id)
    {
        this.id = id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(final String category)
    {
        this.category = category;
    }

    public String getQuestionTitle()
    {
        return questionTitle;
    }

    public void setQuestionTitle(final String questionTitle)
    {
        this.questionTitle = questionTitle;
    }

    public String getDifficultyLevel()
    {
        return difficultyLevel;
    }

    public void setDifficultyLevel(final String difficultyLevel)
    {
        this.difficultyLevel = difficultyLevel;
    }

    public String getOption1()
    {
        return option1;
    }

    public void setOption1(final String option1)
    {
        this.option1 = option1;
    }

    public String getOption2()
    {
        return option2;
    }

    public void setOption2(final String option2)
    {
        this.option2 = option2;
    }

    public String getOption3()
    {
        return option3;
    }

    public void setOption3(final String option3)
    {
        this.option3 = option3;
    }

    public String getOption4()
    {
        return option4;
    }

    public void setOption4(final String option4)
    {
        this.option4 = option4;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer(final String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }

    public List<QuizEntity> getQuizzes()
    {
        return quizzes;
    }

    public void setQuizzes(final List<QuizEntity> quizzes)
    {
        this.quizzes = quizzes;
    }
}
