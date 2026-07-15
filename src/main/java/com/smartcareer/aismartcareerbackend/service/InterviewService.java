package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.InterviewRequest;
import com.smartcareer.aismartcareerbackend.dto.InterviewResponse;
import com.smartcareer.aismartcareerbackend.entities.Application;
import com.smartcareer.aismartcareerbackend.entities.Interview;
import com.smartcareer.aismartcareerbackend.mapper.InterviewMapper;
import com.smartcareer.aismartcareerbackend.repository.ApplicationRepository;
import com.smartcareer.aismartcareerbackend.repository.InterviewRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewResponse schedule(Long applicationId, InterviewRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (!application.getJobOffer().getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à planifier un entretien pour cette candidature");
        }

        if (interviewRepository.findByApplicationId(applicationId).isPresent()) {
            throw new RuntimeException("Un entretien est déjà planifié pour cette candidature");
        }

        Interview interview = Interview.builder()
                .date(request.getDate())
                .time(request.getTime())
                .location(request.getLocation())
                .type(request.getType())
                .meetingLink(request.getMeetingLink())
                .application(application)
                .build();

        interviewRepository.save(interview);
        return InterviewMapper.toResponse(interview);
    }

    public InterviewResponse getByApplicationId(Long applicationId) {
        Long userId = SecurityUtils.getCurrentUserId();

        Interview interview = interviewRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Entretien introuvable"));

        boolean isCandidate = interview.getApplication().getCandidate().getId().equals(userId);
        boolean isCompany = interview.getApplication().getJobOffer().getCompany().getId().equals(userId);

        if (!isCandidate && !isCompany) {
            throw new RuntimeException("Vous n'êtes pas autorisé à consulter cet entretien");
        }

        return InterviewMapper.toResponse(interview);
    }

    public InterviewResponse setResult(Long id, String result) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entretien introuvable"));

        if (!interview.getApplication().getJobOffer().getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cet entretien");
        }

        interview.setResult(result);
        interviewRepository.save(interview);
        return InterviewMapper.toResponse(interview);
    }

    public void cancel(Long id) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entretien introuvable"));

        if (!interview.getApplication().getJobOffer().getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à annuler cet entretien");
        }

        interviewRepository.deleteById(id);
    }
}