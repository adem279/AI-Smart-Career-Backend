package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.JobOfferResponse;
import com.smartcareer.aismartcareerbackend.entities.JobOffer;

public class JobOfferMapper {

    public static JobOfferResponse toResponse(JobOffer jobOffer) {
        return JobOfferResponse.builder()
                .id(jobOffer.getId())
                .title(jobOffer.getTitle())
                .description(jobOffer.getDescription())
                .salary(jobOffer.getSalary())
                .location(jobOffer.getLocation())
                .jobType(jobOffer.getJobType().name())
                .experienceLevel(jobOffer.getExperienceLevel().name())
                .deadline(jobOffer.getDeadline())
                .status(jobOffer.getStatus().name())
                .companyId(jobOffer.getCompany().getId())
                .companyName(jobOffer.getCompany().getCompanyName())
                .build();
    }
}