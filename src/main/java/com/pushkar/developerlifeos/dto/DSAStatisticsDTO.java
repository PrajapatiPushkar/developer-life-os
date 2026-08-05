package com.pushkar.developerlifeos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DSAStatisticsDTO {

    private long totalProblems;

    private long solvedProblems;

    private long unsolvedProblems;

    private long easyProblems;

    private long mediumProblems;

    private long hardProblems;

}