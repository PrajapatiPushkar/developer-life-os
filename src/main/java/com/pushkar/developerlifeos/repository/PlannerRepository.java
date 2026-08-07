package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.entity.TimeSlot;
import com.pushkar.developerlifeos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    List<Planner> findByUser(User user);

    Optional<Planner> findByIdAndUser(Long id, User user);

    List<Planner> findByUserAndPlannerDate(
            User user,
            LocalDate plannerDate
    );

    List<Planner> findByUserAndTimeSlot(
            User user,
            TimeSlot timeSlot
    );

    List<Planner> findByUserAndPlannerDateAndTimeSlot(
            User user,
            LocalDate plannerDate,
            TimeSlot timeSlot
    );

    List<Planner> findByUserAndPlannerDateAndCompletedFalse(
            User user,
            LocalDate plannerDate
    );

    long countByUser(User user);

    long countByUserAndCompleted(
            User user,
            boolean completed
    );

    long countByUserAndTimeSlot(
            User user,
            TimeSlot timeSlot
    );
}