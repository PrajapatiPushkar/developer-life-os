package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.Difficulty;
import com.pushkar.developerlifeos.entity.Platform;
import com.pushkar.developerlifeos.entity.Topic;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProblemResponseDTO {

    private Long id;

    private String title;

    private Platform platform;

    private Difficulty difficulty;

    private Topic topic;

    private boolean solved;

    private String problemLink;

    private String solutionLink;

    private String notes;

    private LocalDate solvedDate;

}