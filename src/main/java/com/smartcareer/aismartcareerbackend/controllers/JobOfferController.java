package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.JobOfferRequest;
import com.smartcareer.aismartcareerbackend.dto.JobOfferResponse;
import com.smartcareer.aismartcareerbackend.service.JobOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @PostMapping
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest request) {
        return ResponseEntity.ok(jobOfferService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<JobOfferResponse>> getAllOpen() {
        return ResponseEntity.ok(jobOfferService.getAllOpen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobOfferService.getById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<JobOfferResponse>> getMyJobOffers() {
        return ResponseEntity.ok(jobOfferService.getMyJobOffers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOfferResponse> update(@PathVariable Long id, @Valid @RequestBody JobOfferRequest request) {
        return ResponseEntity.ok(jobOfferService.update(id, request));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobOfferResponse> closeOffer(@PathVariable Long id) {
        return ResponseEntity.ok(jobOfferService.closeOffer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobOfferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
