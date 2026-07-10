package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CandidateUpdateRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String bio;
    private String photo;
}
