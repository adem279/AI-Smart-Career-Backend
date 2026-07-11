package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.PortfolioRequest;
import com.smartcareer.aismartcareerbackend.dto.PortfolioResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Portfolio;
import com.smartcareer.aismartcareerbackend.mapper.PortfolioMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.PortfolioRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final CandidateRepository candidateRepository;

    public PortfolioResponse create(PortfolioRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        Portfolio portfolio = Portfolio.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .projectUrl(request.getProjectUrl())
                .githubUrl(request.getGithubUrl())
                .candidate(candidate)
                .build();

        portfolioRepository.save(portfolio);
        return PortfolioMapper.toResponse(portfolio);
    }

    public List<PortfolioResponse> getMyPortfolios() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return portfolioRepository.findByCandidateId(candidateId)
                .stream()
                .map(PortfolioMapper::toResponse)
                .toList();
    }

    public PortfolioResponse update(Long id, PortfolioRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (!portfolio.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce projet");
        }

        portfolio.setTitle(request.getTitle());
        portfolio.setDescription(request.getDescription());
        portfolio.setProjectUrl(request.getProjectUrl());
        portfolio.setGithubUrl(request.getGithubUrl());

        portfolioRepository.save(portfolio);
        return PortfolioMapper.toResponse(portfolio);
    }

    public void delete(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (!portfolio.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce projet");
        }

        portfolioRepository.deleteById(id);
    }
}