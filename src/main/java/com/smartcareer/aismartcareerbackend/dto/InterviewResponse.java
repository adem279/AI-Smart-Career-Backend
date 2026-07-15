package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InterviewResponse {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String type;
    private String result;
    private Long applicationId;
    private String meetingLink;
}
