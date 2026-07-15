package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.CandidateSkillRequest;
import com.smartcareer.aismartcareerbackend.dto.CandidateSkillResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.CandidateSkill;
import com.smartcareer.aismartcareerbackend.entities.Skill;
import com.smartcareer.aismartcareerbackend.mapper.CandidateSkillMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.CandidateSkillRepository;
import com.smartcareer.aismartcareerbackend.repository.SkillRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateRepository candidateRepository;
    private final SkillRepository skillRepository;

    public CandidateSkillResponse addSkill(CandidateSkillRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        if (candidateSkillRepository.existsByCandidateIdAndSkillId(candidateId, request.getSkillId())) {
            throw new RuntimeException("Cette compétence est déjà associée à votre profil");
        }

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));

        CandidateSkill candidateSkill = CandidateSkill.builder()
                .candidate(candidate)
                .skill(skill)
                .level(request.getLevel())
                .build();

        candidateSkillRepository.save(candidateSkill);
        return CandidateSkillMapper.toResponse(candidateSkill);
    }
    public List<CandidateSkillResponse> getByCandidateId(Long candidateId) {
        return candidateSkillRepository.findByCandidateId(candidateId)
                .stream()
                .map(CandidateSkillMapper::toResponse)
                .toList();
    }
    public List<CandidateSkillResponse> getMySkills() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return candidateSkillRepository.findByCandidateId(candidateId)
                .stream()
                .map(CandidateSkillMapper::toResponse)
                .toList();
    }

    public CandidateSkillResponse updateLevel(Long id, CandidateSkillRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        CandidateSkill candidateSkill = candidateSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));

        if (!candidateSkill.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette compétence");
        }

        candidateSkill.setLevel(request.getLevel());
        candidateSkillRepository.save(candidateSkill);
        return CandidateSkillMapper.toResponse(candidateSkill);
    }

    public void removeSkill(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        CandidateSkill candidateSkill = candidateSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));

        if (!candidateSkill.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette compétence");
        }

        candidateSkillRepository.deleteById(id);
    }
}