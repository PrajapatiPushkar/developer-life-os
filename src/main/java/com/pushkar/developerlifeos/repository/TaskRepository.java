package com.pushkar.developerlifeos.repository;

import com.pushkar.developerlifeos.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}