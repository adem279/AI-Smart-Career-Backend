package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.SkillResponse;
import com.smartcareer.aismartcareerbackend.entities.Skill;

public class SkillMapper {

    public static SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}