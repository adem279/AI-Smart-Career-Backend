package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.PortfolioRequest;
import com.smartcareer.aismartcareerbackend.dto.PortfolioResponse;
import com.smartcareer.aismartcareerbackend.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<PortfolioResponse> create(@Valid @RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PortfolioResponse>> getMyPortfolios() {
        return ResponseEntity.ok(portfolioService.getMyPortfolios());
    }
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<PortfolioResponse>> getByCandidateId(@PathVariable Long candidateId) {
        return ResponseEntity.ok(portfolioService.getByCandidateId(candidateId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioResponse> update(@PathVariable Long id, @Valid @RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        portfolioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
