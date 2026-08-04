package com.pushkar.developerlifeos.dto;

import lombok.Data;

@Data
public class MilestoneRequestDTO {

    private String title;

    private String description;

    private boolean completed;

    private Long goalId;

}