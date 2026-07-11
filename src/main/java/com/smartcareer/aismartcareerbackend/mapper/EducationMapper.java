package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.EducationResponse;
import com.smartcareer.aismartcareerbackend.entities.Education;

public class EducationMapper {

    public static EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .university(education.getUniversity())
                .degree(education.getDegree())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .build();
    }
}
