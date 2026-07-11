package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.CandidateUpdateRequest;
import com.smartcareer.aismartcareerbackend.dto.CandidateResponse;
import com.smartcareer.aismartcareerbackend.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/me")
    public ResponseEntity<CandidateResponse> getMyProfile() {
        return ResponseEntity.ok(candidateService.getMyProfile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getById(id));
    }

    @PutMapping("/me")
    public ResponseEntity<CandidateResponse> updateMyProfile(@RequestBody CandidateUpdateRequest request) {
        return ResponseEntity.ok(candidateService.updateMyProfile(request));
    }
}
