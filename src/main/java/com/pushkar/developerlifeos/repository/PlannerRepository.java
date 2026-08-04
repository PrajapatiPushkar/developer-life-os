package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Planner;
import com.pushkar.developerlifeos.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    List<Planner> findByPlannerDate(LocalDate plannerDate);

    List<Planner> findByTimeSlot(TimeSlot timeSlot);

    List<Planner> findByPlannerDateAndTimeSlot(
            LocalDate plannerDate,
            TimeSlot timeSlot
    );

    List<Planner> findByPlannerDateAndCompletedFalse(LocalDate plannerDate);

}