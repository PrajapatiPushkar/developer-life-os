package com.pushkar.developerlifeos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    private boolean completed;

    private String priority;

    private LocalDate dueDate;

}