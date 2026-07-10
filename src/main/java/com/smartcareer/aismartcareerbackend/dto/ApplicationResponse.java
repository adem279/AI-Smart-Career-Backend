package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationResponse {

    private Long id;
    private LocalDateTime applicationDate;
    private String status;
    private String message;

    private Long candidateId;
    private String candidateName;

    private Long jobOfferId;
    private String jobOfferTitle;
}
