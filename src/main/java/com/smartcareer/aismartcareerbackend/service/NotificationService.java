package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.NotificationResponse;
import com.smartcareer.aismartcareerbackend.entities.Notification;
import com.smartcareer.aismartcareerbackend.entities.User;
import com.smartcareer.aismartcareerbackend.mapper.NotificationMapper;
import com.smartcareer.aismartcareerbackend.repository.NotificationRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // Appelée en interne par d'autres services (ex: ApplicationService), pas par un controller
    public void createNotification(User user, String title, String message) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .build();
        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getMyNotifications() {
        Long userId = SecurityUtils.getCurrentUserId();
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    public long getUnreadCount() {
        Long userId = SecurityUtils.getCurrentUserId();
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public NotificationResponse markAsRead(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette notification");
        }

        notification.setIsRead(true);
        notificationRepository.save(notification);
        return NotificationMapper.toResponse(notification);
    }

    public void delete(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette notification");
        }

        notificationRepository.deleteById(id);
    }
}
