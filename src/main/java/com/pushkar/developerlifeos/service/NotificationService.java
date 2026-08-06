package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.NotificationRequestDTO;
import com.pushkar.developerlifeos.dto.NotificationResponseDTO;
import com.pushkar.developerlifeos.entity.Notification;
import com.pushkar.developerlifeos.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    public NotificationService(
            NotificationRepository notificationRepository,
            ModelMapper modelMapper) {

        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    // Create Notification
    public Notification create(NotificationRequestDTO dto) {

        Notification notification =
                modelMapper.map(dto, Notification.class);

        return notificationRepository.save(notification);
    }

    // Get All Notifications
    public List<NotificationResponseDTO> getAll() {

        return notificationRepository
                .findAllByOrderByCreatedAtDesc()

                .stream()

                .map(notification ->
                        modelMapper.map(
                                notification,
                                NotificationResponseDTO.class))

                .toList();
    }

    // Get Unread Notifications
    public List<NotificationResponseDTO> getUnread() {

        return notificationRepository
                .findByIsReadFalseOrderByCreatedAtDesc()

                .stream()

                .map(notification ->
                        modelMapper.map(
                                notification,
                                NotificationResponseDTO.class))

                .toList();
    }

    // Mark as Read
    public Notification markAsRead(Long id) {

        Notification notification = notificationRepository

                .findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        notification.setRead(true);

        return notificationRepository.save(notification);

    }

    // Delete Notification
    public void deleteNotification(Long id){

        notificationRepository.deleteById(id);

    }

}