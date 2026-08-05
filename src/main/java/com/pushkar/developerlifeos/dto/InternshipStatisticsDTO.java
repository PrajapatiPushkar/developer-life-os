package com.pushkar.developerlifeos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipStatisticsDTO {

    private long totalApplications;

    private long applied;

    private long onlineAssessment;

    private long interview;

    private long hrRound;

    private long selected;

    private long rejected;

    private long offerAccepted;

}