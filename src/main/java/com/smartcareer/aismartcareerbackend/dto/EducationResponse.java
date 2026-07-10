package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EducationResponse {

    private Long id;
    private String university;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
}
