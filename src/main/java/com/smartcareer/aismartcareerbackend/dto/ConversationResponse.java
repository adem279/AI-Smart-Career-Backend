package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConversationResponse {

    private Long applicationId;
    private String applicationStatus;

    private Long jobOfferId;
    private String jobOfferTitle;

    private Long candidateId;
    private String candidateName;
    private String candidatePhoto;

    private Long companyId;
    private String companyName;
    private String companyLogo;

    private String lastMessage;
    private LocalDateTime lastMessageDate;
    private long unreadCount;

    // Contexte entretien intégré directement dans la conversation
    private InterviewResponse interview;
}