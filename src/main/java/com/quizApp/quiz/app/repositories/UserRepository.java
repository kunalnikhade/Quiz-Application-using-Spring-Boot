package com.quizApp.quiz.app.repositories;

import com.quizApp.quiz.app.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
    Optional<UserEntity> findByEmail(final String username);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.password = ?2 where u.email = ?1")
    void updatePassword(final String email, final String newPassword);
}
