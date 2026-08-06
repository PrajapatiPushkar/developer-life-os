package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.NotificationType;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    private String message;

    private NotificationType type;

}