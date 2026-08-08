package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Platform;
import com.pushkar.developerlifeos.entity.Problem;
import com.pushkar.developerlifeos.entity.Topic;
import com.pushkar.developerlifeos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends
        JpaRepository<Problem, Long>,
        JpaSpecificationExecutor<Problem> {

    // ==========================
    // User Specific
    // ==========================

    List<Problem> findByUser(User user);

    Optional<Problem> findByIdAndUser(
            Long id,
            User user
    );

    // ==========================
    // Filters
    // ==========================

    List<Problem> findByUserAndDifficulty(
            User user,
            Difficulty difficulty
    );

    List<Problem> findByUserAndPlatform(
            User user,
            Platform platform
    );

    List<Problem> findByUserAndTopic(
            User user,
            Topic topic
    );

    List<Problem> findByUserAndSolved(
            User user,
            boolean solved
    );

    // ==========================
    // Statistics
    // ==========================

    long countByUser(User user);

    long countByUserAndSolved(
            User user,
            boolean solved
    );

    long countByUserAndDifficulty(
            User user,
            Difficulty difficulty
    );

    long countByUserAndPlatform(
            User user,
            Platform platform
    );

    long countByUserAndTopic(
            User user,
            Topic topic
    );

}