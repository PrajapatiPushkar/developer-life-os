package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Category;
import com.pushkar.developerlifeos.entity.Priority;
import com.pushkar.developerlifeos.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends
        JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {


    Page<Task> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    long countByCompleted(boolean completed);
    long countByPriority(Priority priority);
    long countByCategory(Category category);

    List<Task> findTop5ByOrderByDueDateAsc();

    long countByDueDateBeforeAndCompletedFalse(LocalDate date);

    long countByDueDateAndCompletedFalse(LocalDate date);
}