package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.CompanyResponse;
import com.smartcareer.aismartcareerbackend.entities.Company;

public class CompanyMapper {

    public static CompanyResponse toResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .firstName(company.getFirstName())
                .lastName(company.getLastName())
                .email(company.getEmail())
                .phone(company.getPhone())
                .createdAt(company.getCreatedAt())
                .companyName(company.getCompanyName())
                .sector(company.getSector())
                .address(company.getAddress())
                .website(company.getWebsite())
                .logo(company.getLogo())
                .description(company.getDescription())
                .build();
    }
}