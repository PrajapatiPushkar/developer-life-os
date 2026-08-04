package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.TimeSlot;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlannerResponseDTO {

    private Long id;

    private String title;

    private String description;

    private TimeSlot timeSlot;

    private boolean completed;

    private LocalDate plannerDate;

}