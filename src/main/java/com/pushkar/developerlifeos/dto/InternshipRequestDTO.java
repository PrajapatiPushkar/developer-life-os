package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.InternshipStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InternshipRequestDTO {

    private String companyName;

    private String role;

    private String location;

    private LocalDate applicationDate;

    private LocalDate deadline;

    private InternshipStatus status;

    private String jobLink;

    private String salary;

    private String notes;

}