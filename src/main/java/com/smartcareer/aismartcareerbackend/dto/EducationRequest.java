package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EducationRequest {

    @NotBlank private String university;
    @NotBlank private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
}
