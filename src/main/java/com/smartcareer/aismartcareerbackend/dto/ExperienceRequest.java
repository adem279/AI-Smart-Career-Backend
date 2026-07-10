package com.smartcareer.aismartcareerbackend.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExperienceRequest {

    @NotBlank private String company;
    @NotBlank private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}