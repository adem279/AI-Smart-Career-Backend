package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.InterviewResponse;
import com.smartcareer.aismartcareerbackend.entities.Interview;

public class InterviewMapper {

    public static InterviewResponse toResponse(Interview interview) {
        return InterviewResponse.builder()
                .id(interview.getId())
                .date(interview.getDate())
                .time(interview.getTime())
                .location(interview.getLocation())
                .type(interview.getType())
                .result(interview.getResult())
                .meetingLink(interview.getMeetingLink())
                .applicationId(interview.getApplication().getId())
                .build();
    }
}