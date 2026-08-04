package com.pushkar.developerlifeos.dto;

import lombok.Data;

@Data
public class MilestoneResponseDTO {

    private Long id;

    private String title;

    private String description;

    private boolean completed;

}