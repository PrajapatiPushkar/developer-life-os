package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Goal;
import com.pushkar.developerlifeos.entity.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    long countByStatus(GoalStatus status);

}