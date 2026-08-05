package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Platform;
import com.pushkar.developerlifeos.entity.Problem;
import com.pushkar.developerlifeos.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByDifficulty(Difficulty difficulty);

    List<Problem> findByPlatform(Platform platform);

    List<Problem> findByTopic(Topic topic);

    List<Problem> findBySolved(boolean solved);

    long countBySolved(boolean solved);

    long countByDifficulty(Difficulty difficulty);

    long countByPlatform(Platform platform);

    long countByTopic(Topic topic);

}