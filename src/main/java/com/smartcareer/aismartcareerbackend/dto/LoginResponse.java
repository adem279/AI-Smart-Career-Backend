package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {

    private String token;
    private Long userId;
    private String email;
    private String userType; // "CANDIDATE" ou "COMPANY"
}
