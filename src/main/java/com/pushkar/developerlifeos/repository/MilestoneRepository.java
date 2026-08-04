package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository
        extends JpaRepository<Milestone, Long> {

    List<Milestone> findByGoalId(Long goalId);

}