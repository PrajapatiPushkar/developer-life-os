package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.Category;
import com.pushkar.developerlifeos.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    private boolean completed;

    private String priority;

    private LocalDate dueDate;

    private TaskStatus status;

    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}