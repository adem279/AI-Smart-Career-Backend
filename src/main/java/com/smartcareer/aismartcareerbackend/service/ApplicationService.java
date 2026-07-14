package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.ApplicationRequest;
import com.smartcareer.aismartcareerbackend.dto.ApplicationResponse;
import com.smartcareer.aismartcareerbackend.entities.*;
import com.smartcareer.aismartcareerbackend.mapper.ApplicationMapper;
import com.smartcareer.aismartcareerbackend.repository.ApplicationRepository;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.JobOfferRepository;
import com.smartcareer.aismartcareerbackend.repository.ResumeRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ResumeRepository resumeRepository;
    private final NotificationService notificationService;

    public ApplicationResponse apply(ApplicationRequest request) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        if (applicationRepository.existsByCandidateIdAndJobOfferId(candidateId, request.getJobOfferId())) {
            throw new RuntimeException("Vous avez déjà postulé à cette offre");
        }

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        JobOffer jobOffer = jobOfferRepository.findById(request.getJobOfferId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (jobOffer.getStatus() != JobOfferStatus.OPEN) {
            throw new RuntimeException("Cette offre n'accepte plus de candidatures");
        }

        Application.ApplicationBuilder applicationBuilder = Application.builder()
                .candidate(candidate)
                .jobOffer(jobOffer)
                .message(request.getMessage());

        if (request.getResumeId() != null) {
            Resume resume = resumeRepository.findById(request.getResumeId())
                    .orElseThrow(() -> new RuntimeException("CV introuvable"));

            if (!resume.getCandidate().getId().equals(candidateId)) {
                throw new RuntimeException("Ce CV ne vous appartient pas");
            }

            applicationBuilder.resume(resume);
        }

        Application application = applicationBuilder.build();
        applicationRepository.save(application);
        return ApplicationMapper.toResponse(application);
    }

    public List<ApplicationResponse> getMyApplications() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return applicationRepository.findByCandidateId(candidateId)
                .stream()
                .map(ApplicationMapper::toResponse)
                .toList();
    }

    public List<ApplicationResponse> getApplicationsForJobOffer(Long jobOfferId) {
        Long companyId = SecurityUtils.getCurrentUserId();

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!jobOffer.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à consulter ces candidatures");
        }

        return applicationRepository.findByJobOfferId(jobOfferId)
                .stream()
                .map(ApplicationMapper::toResponse)
                .toList();
    }

    public ApplicationResponse accept(Long id) {
        return updateStatus(id, ApplicationStatus.ACCEPTED);
    }

    public ApplicationResponse reject(Long id) {
        return updateStatus(id, ApplicationStatus.REJECTED);
    }

    private ApplicationResponse updateStatus(Long id, ApplicationStatus status) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (!application.getJobOffer().getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette candidature");
        }

        application.setStatus(status);
        applicationRepository.save(application);

        String title = status == ApplicationStatus.ACCEPTED ? "Candidature acceptée" : "Candidature refusée";
        String message = "Votre candidature pour '" + application.getJobOffer().getTitle() + "' a été " +
                (status == ApplicationStatus.ACCEPTED ? "acceptée" : "refusée") + ".";

        notificationService.createNotification(application.getCandidate(), title, message);

        return ApplicationMapper.toResponse(application);
    }

    public void withdraw(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (!application.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à retirer cette candidature");
        }

        applicationRepository.deleteById(id);
    }
}