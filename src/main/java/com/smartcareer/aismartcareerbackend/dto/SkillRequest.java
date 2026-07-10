package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SkillRequest {

    @NotBlank private String name;
}