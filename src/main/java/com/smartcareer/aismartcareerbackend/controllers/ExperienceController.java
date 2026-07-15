package com.smartcareer.aismartcareerbackend.controllers;
import com.smartcareer.aismartcareerbackend.dto.ExperienceRequest;
import com.smartcareer.aismartcareerbackend.dto.ExperienceResponse;
import com.smartcareer.aismartcareerbackend.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ExperienceResponse> create(@Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(experienceService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ExperienceResponse>> getMyExperiences() {
        return ResponseEntity.ok(experienceService.getMyExperiences());
    }
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ExperienceResponse>> getByCandidateId(@PathVariable Long candidateId) {
        return ResponseEntity.ok(experienceService.getByCandidateId(candidateId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceResponse> update(@PathVariable Long id, @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(experienceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
