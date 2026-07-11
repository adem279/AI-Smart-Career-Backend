package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.SkillRequest;
import com.smartcareer.aismartcareerbackend.dto.SkillResponse;
import com.smartcareer.aismartcareerbackend.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> create(@Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAll() {
        return ResponseEntity.ok(skillService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> update(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}