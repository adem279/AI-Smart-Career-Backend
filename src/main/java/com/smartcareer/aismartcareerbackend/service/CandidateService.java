package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.CandidateUpdateRequest;
import com.smartcareer.aismartcareerbackend.dto.CandidateResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.mapper.CandidateMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateResponse getMyProfile() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));
        return CandidateMapper.toResponse(candidate);
    }

    public CandidateResponse getById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));
        return CandidateMapper.toResponse(candidate);
    }

    public CandidateResponse updateMyProfile(CandidateUpdateRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        if (request.getFirstName() != null) candidate.setFirstName(request.getFirstName());
        if (request.getLastName() != null) candidate.setLastName(request.getLastName());
        if (request.getPhone() != null) candidate.setPhone(request.getPhone());
        if (request.getAddress() != null) candidate.setAddress(request.getAddress());
        if (request.getBirthDate() != null) candidate.setBirthDate(request.getBirthDate());
        if (request.getBio() != null) candidate.setBio(request.getBio());
        if (request.getPhoto() != null) candidate.setPhoto(request.getPhoto());

        candidateRepository.save(candidate);
        return CandidateMapper.toResponse(candidate);
    }
}
