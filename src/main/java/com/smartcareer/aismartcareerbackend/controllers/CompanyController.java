package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.CompanyUpdateRequest;
import com.smartcareer.aismartcareerbackend.dto.CompanyResponse;
import com.smartcareer.aismartcareerbackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/me")
    public ResponseEntity<CompanyResponse> getMyProfile() {
        return ResponseEntity.ok(companyService.getMyProfile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @PutMapping("/me")
    public ResponseEntity<CompanyResponse> updateMyProfile(@RequestBody CompanyUpdateRequest request) {
        return ResponseEntity.ok(companyService.updateMyProfile(request));
    }
}