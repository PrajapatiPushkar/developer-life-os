package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.GoalCategory;
import com.pushkar.developerlifeos.entity.GoalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalResponseDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDate targetDate;

    private Integer progress;

    private GoalStatus status;

    private GoalCategory category;

}