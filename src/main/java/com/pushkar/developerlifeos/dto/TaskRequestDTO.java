package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDTO {

    private String title;

    private String description;

    private boolean completed;

    private Priority priority;

    private LocalDate dueDate;

}