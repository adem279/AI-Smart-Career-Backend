package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobOfferResponse {

    private Long id;
    private String title;
    private String description;
    private Double salary;
    private String location;
    private String jobType;
    private String experienceLevel;
    private LocalDate deadline;
    private String status;
    private Long companyId;
    private String companyName;
}