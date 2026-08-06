package com.pushkar.developerlifeos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemStatisticsDTO {

    private long totalProblems;

    private long solvedProblems;

    private long unsolvedProblems;

    private long easyProblems;

    private long mediumProblems;

    private long hardProblems;

}