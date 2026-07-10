package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyUpdateRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String companyName;
    private String sector;
    private String address;
    private String website;
    private String logo;
    private String description;
}