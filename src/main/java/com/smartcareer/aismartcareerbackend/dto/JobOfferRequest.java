package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobOfferRequest {

    @NotBlank private String title;
    private String description;
    private Double salary;
    private String location;
    private String jobType;
    private String experienceLevel;
    private LocalDate deadline;
}
