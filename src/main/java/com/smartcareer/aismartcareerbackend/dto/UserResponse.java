package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private String roleName;
}
