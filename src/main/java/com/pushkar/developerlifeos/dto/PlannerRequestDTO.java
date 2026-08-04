package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.TimeSlot;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlannerRequestDTO {

    private String title;

    private String description;

    private TimeSlot timeSlot;

    private LocalDate plannerDate;

}