package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.ReviewRequest;
import com.smartcareer.aismartcareerbackend.dto.ReviewResponse;
import com.smartcareer.aismartcareerbackend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.create(request));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(reviewService.getReviewsForCandidate(candidateId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> getMyReviewsWritten() {
        return ResponseEntity.ok(reviewService.getMyReviewsWritten());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}