package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.EducationRequest;
import com.smartcareer.aismartcareerbackend.dto.EducationResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Education;
import com.smartcareer.aismartcareerbackend.mapper.EducationMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.EducationRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final CandidateRepository candidateRepository;

    public EducationResponse create(EducationRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        Education education = Education.builder()
                .university(request.getUniversity())
                .degree(request.getDegree())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .candidate(candidate)
                .build();

        educationRepository.save(education);
        return EducationMapper.toResponse(education);
    }

    public List<EducationResponse> getMyEducations() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return educationRepository.findByCandidateId(candidateId)
                .stream()
                .map(EducationMapper::toResponse)
                .toList();
    }

    public EducationResponse update(Long id, EducationRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));

        if (!education.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette formation");
        }

        education.setUniversity(request.getUniversity());
        education.setDegree(request.getDegree());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());

        educationRepository.save(education);
        return EducationMapper.toResponse(education);
    }

    public void delete(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));

        if (!education.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette formation");
        }

        educationRepository.deleteById(id);
    }
}
