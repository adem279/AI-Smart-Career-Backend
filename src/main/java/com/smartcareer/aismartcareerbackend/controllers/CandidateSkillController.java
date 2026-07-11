package com.smartcareer.aismartcareerbackend.controllers;
import com.smartcareer.aismartcareerbackend.dto.CandidateSkillRequest;
import com.smartcareer.aismartcareerbackend.dto.CandidateSkillResponse;
import com.smartcareer.aismartcareerbackend.service.CandidateSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-skills")
@RequiredArgsConstructor
public class CandidateSkillController {

    private final CandidateSkillService candidateSkillService;

    @PostMapping
    public ResponseEntity<CandidateSkillResponse> addSkill(@Valid @RequestBody CandidateSkillRequest request) {
        return ResponseEntity.ok(candidateSkillService.addSkill(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<CandidateSkillResponse>> getMySkills() {
        return ResponseEntity.ok(candidateSkillService.getMySkills());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateSkillResponse> updateLevel(@PathVariable Long id, @Valid @RequestBody CandidateSkillRequest request) {
        return ResponseEntity.ok(candidateSkillService.updateLevel(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSkill(@PathVariable Long id) {
        candidateSkillService.removeSkill(id);
        return ResponseEntity.noContent().build();
    }
}