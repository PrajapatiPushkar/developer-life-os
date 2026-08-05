package com.pushkar.developerlifeos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerStatisticsDTO {

    private long totalPlans;

    private long completedPlans;

    private long pendingPlans;

    private long morningPlans;

    private long afternoonPlans;

    private long eveningPlans;

    private long nightPlans;

}