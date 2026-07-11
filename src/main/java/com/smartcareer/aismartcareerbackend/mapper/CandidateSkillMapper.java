package com.smartcareer.aismartcareerbackend.mapper;
import com.smartcareer.aismartcareerbackend.dto.CandidateSkillResponse;
import com.smartcareer.aismartcareerbackend.entities.CandidateSkill;

public class CandidateSkillMapper {

    public static CandidateSkillResponse toResponse(CandidateSkill candidateSkill) {
        return CandidateSkillResponse.builder()
                .id(candidateSkill.getId())
                .skillId(candidateSkill.getSkill().getId())
                .skillName(candidateSkill.getSkill().getName())
                .level(candidateSkill.getLevel())
                .build();
    }
}
