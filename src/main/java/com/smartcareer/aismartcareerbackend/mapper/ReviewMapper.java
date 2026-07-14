package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.ReviewResponse;
import com.smartcareer.aismartcareerbackend.entities.Review;

public class ReviewMapper {

    public static ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .companyId(review.getCompany().getId())
                .companyName(review.getCompany().getCompanyName())
                .candidateId(review.getCandidate().getId())
                .candidateName(review.getCandidate().getFirstName() + " " + review.getCandidate().getLastName())
                .build();
    }
}
