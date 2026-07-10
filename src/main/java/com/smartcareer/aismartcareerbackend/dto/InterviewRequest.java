package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InterviewRequest {

    @NotNull private LocalDate date;
    @NotNull private LocalTime time;
    private String location;
    private String type;
}