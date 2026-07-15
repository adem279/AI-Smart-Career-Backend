package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageRequest {

    @NotNull private Long applicationId;
    @NotBlank private String content;
}