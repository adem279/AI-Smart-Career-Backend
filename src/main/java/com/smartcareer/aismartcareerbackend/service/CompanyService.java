package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.CompanyUpdateRequest;
import com.smartcareer.aismartcareerbackend.dto.CompanyResponse;
import com.smartcareer.aismartcareerbackend.entities.Company;
import com.smartcareer.aismartcareerbackend.mapper.CompanyMapper;
import com.smartcareer.aismartcareerbackend.repository.CompanyRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse getMyProfile() {
        Long companyId = SecurityUtils.getCurrentUserId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
        return CompanyMapper.toResponse(company);
    }

    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
        return CompanyMapper.toResponse(company);
    }

    public CompanyResponse updateMyProfile(CompanyUpdateRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        if (request.getFirstName() != null) company.setFirstName(request.getFirstName());
        if (request.getLastName() != null) company.setLastName(request.getLastName());
        if (request.getPhone() != null) company.setPhone(request.getPhone());
        if (request.getCompanyName() != null) company.setCompanyName(request.getCompanyName());
        if (request.getSector() != null) company.setSector(request.getSector());
        if (request.getAddress() != null) company.setAddress(request.getAddress());
        if (request.getWebsite() != null) company.setWebsite(request.getWebsite());
        if (request.getLogo() != null) company.setLogo(request.getLogo());
        if (request.getDescription() != null) company.setDescription(request.getDescription());

        companyRepository.save(company);
        return CompanyMapper.toResponse(company);
    }
}
