package com.smartcareer.aismartcareerbackend.mapper;
import com.smartcareer.aismartcareerbackend.dto.NotificationResponse;
import com.smartcareer.aismartcareerbackend.entities.Notification;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
