package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageResponse {

    private Long id;
    private Long applicationId;
    private Long senderId;
    private String senderType;
    private String senderName;
    private String content;
    private LocalDateTime sentAt;
    private Boolean isRead;
}