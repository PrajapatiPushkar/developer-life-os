package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.NotificationRequestDTO;
import com.pushkar.developerlifeos.dto.NotificationResponseDTO;
import com.pushkar.developerlifeos.entity.Notification;
import com.pushkar.developerlifeos.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> create(

            @RequestBody NotificationRequestDTO dto) {

        return ResponseEntity.ok(

                notificationService.create(dto));

    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll() {

        return ResponseEntity.ok(

                notificationService.getAll());

    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnread() {

        return ResponseEntity.ok(

                notificationService.getUnread());

    }

}