package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.InternshipStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InternshipResponseDTO {

    private Long id;

    private String companyName;

    private String role;

    private String location;

    private LocalDate applicationDate;

    private LocalDate deadline;

    private InternshipStatus status;

    private String jobLink;

    private String salary;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}