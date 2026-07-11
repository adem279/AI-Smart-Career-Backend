package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.ExperienceResponse;
import com.smartcareer.aismartcareerbackend.entities.Experience;

public class ExperienceMapper {

    public static ExperienceResponse toResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .company(experience.getCompany())
                .position(experience.getPosition())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .description(experience.getDescription())
                .build();
    }
}