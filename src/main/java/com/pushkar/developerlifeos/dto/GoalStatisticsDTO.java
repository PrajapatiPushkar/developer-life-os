package com.pushkar.developerlifeos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalStatisticsDTO {

    private long totalGoals;

    private long completedGoals;

    private long inProgressGoals;

    private long notStartedGoals;

}