package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterCompanyRequest {

    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email private String email;
    @NotBlank @Size(min = 6) private String password;
    private String phone;

    @NotBlank private String companyName;
    private String sector;
}