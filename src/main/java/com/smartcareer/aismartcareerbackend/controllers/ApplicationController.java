package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.ApplicationRequest;
import com.smartcareer.aismartcareerbackend.dto.ApplicationResponse;
import com.smartcareer.aismartcareerbackend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> apply(@Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.apply(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForJobOffer(@PathVariable Long jobOfferId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJobOffer(jobOfferId));
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<ApplicationResponse> accept(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.accept(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApplicationResponse> reject(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.reject(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> withdraw(@PathVariable Long id) {
        applicationService.withdraw(id);
        return ResponseEntity.noContent().build();
    }
}