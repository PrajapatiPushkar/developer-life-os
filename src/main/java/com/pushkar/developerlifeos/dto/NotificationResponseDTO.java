package com.pushkar.developerlifeos.dto;

import com.pushkar.developerlifeos.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long id;

    private String message;

    private NotificationType type;

    private boolean isRead;

    private LocalDateTime createdAt;

}