package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.ApplicationResponse;
import com.smartcareer.aismartcareerbackend.entities.Application;

public class ApplicationMapper {

    public static ApplicationResponse toResponse(Application application) {
        ApplicationResponse.ApplicationResponseBuilder builder = ApplicationResponse.builder()
                .id(application.getId())
                .applicationDate(application.getApplicationDate())
                .status(application.getStatus().name())
                .message(application.getMessage())
                .candidateId(application.getCandidate().getId())
                .candidateName(application.getCandidate().getFirstName() + " " + application.getCandidate().getLastName())
                .jobOfferId(application.getJobOffer().getId())
                .jobOfferTitle(application.getJobOffer().getTitle());

        if (application.getResume() != null) {
            builder.resumeId(application.getResume().getId())
                    .resumeFileName(application.getResume().getFileName())
                    .resumeFilePath(application.getResume().getFilePath());
        }

        return builder.build();
    }
}