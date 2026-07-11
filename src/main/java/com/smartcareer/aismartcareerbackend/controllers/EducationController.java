package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.EducationRequest;
import com.smartcareer.aismartcareerbackend.dto.EducationResponse;
import com.smartcareer.aismartcareerbackend.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> create(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(educationService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<EducationResponse>> getMyEducations() {
        return ResponseEntity.ok(educationService.getMyEducations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationResponse> update(@PathVariable Long id, @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(educationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        educationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
