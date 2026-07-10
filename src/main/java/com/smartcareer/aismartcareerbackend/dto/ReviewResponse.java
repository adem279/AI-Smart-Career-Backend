package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewResponse {

    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    private Long companyId;
    private String companyName;

    private Long candidateId;
    private String candidateName;
}
