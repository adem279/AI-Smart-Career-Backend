package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationRequest {

    @NotNull private Long jobOfferId;
    private String message;
    private Long resumeId;
}
