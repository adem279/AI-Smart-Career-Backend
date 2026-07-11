package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.CandidateResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;

public class CandidateMapper {

    public static CandidateResponse toResponse(Candidate candidate) {
        return CandidateResponse.builder()
                .id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .email(candidate.getEmail())
                .phone(candidate.getPhone())
                .createdAt(candidate.getCreatedAt())
                .address(candidate.getAddress())
                .birthDate(candidate.getBirthDate())
                .bio(candidate.getBio())
                .photo(candidate.getPhoto())
                .build();
    }
}
