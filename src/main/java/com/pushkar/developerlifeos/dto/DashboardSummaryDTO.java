package com.pushkar.developerlifeos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {

    private long totalTasks;

    private long completedTasks;

    private long pendingTasks;

    private long highPriorityTasks;

}