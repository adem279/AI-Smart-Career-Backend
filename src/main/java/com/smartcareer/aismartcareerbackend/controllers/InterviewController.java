package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.InterviewRequest;
import com.smartcareer.aismartcareerbackend.dto.InterviewResponse;
import com.smartcareer.aismartcareerbackend.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/application/{applicationId}")
    public ResponseEntity<InterviewResponse> schedule(@PathVariable Long applicationId, @Valid @RequestBody InterviewRequest request) {
        return ResponseEntity.ok(interviewService.schedule(applicationId, request));
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<InterviewResponse> getByApplicationId(@PathVariable Long applicationId) {
        return ResponseEntity.ok(interviewService.getByApplicationId(applicationId));
    }

    @PatchMapping("/{id}/result")
    public ResponseEntity<InterviewResponse> setResult(@PathVariable Long id, @RequestParam String result) {
        return ResponseEntity.ok(interviewService.setResult(id, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        interviewService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}