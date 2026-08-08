package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Goal;
import com.pushkar.developerlifeos.entity.GoalStatus;
import com.pushkar.developerlifeos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByUser(User user);

    Optional<Goal> findByIdAndUser(Long id, User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, GoalStatus status);

}