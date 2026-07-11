package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.ExperienceRequest;
import com.smartcareer.aismartcareerbackend.dto.ExperienceResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Experience;
import com.smartcareer.aismartcareerbackend.mapper.ExperienceMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.ExperienceRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CandidateRepository candidateRepository;

    public ExperienceResponse create(ExperienceRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        Experience experience = Experience.builder()
                .company(request.getCompany())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .candidate(candidate)
                .build();

        experienceRepository.save(experience);
        return ExperienceMapper.toResponse(experience);
    }

    public List<ExperienceResponse> getMyExperiences() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return experienceRepository.findByCandidateId(candidateId)
                .stream()
                .map(ExperienceMapper::toResponse)
                .toList();
    }

    public ExperienceResponse update(Long id, ExperienceRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expérience introuvable"));

        if (!experience.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette expérience");
        }

        experience.setCompany(request.getCompany());
        experience.setPosition(request.getPosition());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setDescription(request.getDescription());

        experienceRepository.save(experience);
        return ExperienceMapper.toResponse(experience);
    }

    public void delete(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expérience introuvable"));

        if (!experience.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette expérience");
        }

        experienceRepository.deleteById(id);
    }
}
