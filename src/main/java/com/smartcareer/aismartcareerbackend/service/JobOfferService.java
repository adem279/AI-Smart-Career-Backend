package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.JobOfferRequest;
import com.smartcareer.aismartcareerbackend.dto.JobOfferResponse;
import com.smartcareer.aismartcareerbackend.entities.*;
import com.smartcareer.aismartcareerbackend.mapper.JobOfferMapper;
import com.smartcareer.aismartcareerbackend.repository.CompanyRepository;
import com.smartcareer.aismartcareerbackend.repository.JobOfferRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final CompanyRepository companyRepository;

    public JobOfferResponse create(JobOfferRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        JobOffer jobOffer = JobOffer.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .salary(request.getSalary())
                .location(request.getLocation())
                .jobType(JobType.valueOf(request.getJobType()))
                .experienceLevel(ExperienceLevel.valueOf(request.getExperienceLevel()))
                .deadline(request.getDeadline())
                .company(company)
                .build();
        // status reste OPEN par défaut grâce à @Builder.Default dans l'entité

        jobOfferRepository.save(jobOffer);
        return JobOfferMapper.toResponse(jobOffer);
    }

    public List<JobOfferResponse> getAllOpen() {
        return jobOfferRepository.findByStatus(JobOfferStatus.OPEN)
                .stream()
                .map(JobOfferMapper::toResponse)
                .toList();
    }

    public JobOfferResponse getById(Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));
        return JobOfferMapper.toResponse(jobOffer);
    }

    public List<JobOfferResponse> getMyJobOffers() {
        Long companyId = SecurityUtils.getCurrentUserId();
        return jobOfferRepository.findByCompanyId(companyId)
                .stream()
                .map(JobOfferMapper::toResponse)
                .toList();
    }

    public JobOfferResponse update(Long id, JobOfferRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();

        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!jobOffer.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette offre");
        }

        jobOffer.setTitle(request.getTitle());
        jobOffer.setDescription(request.getDescription());
        jobOffer.setSalary(request.getSalary());
        jobOffer.setLocation(request.getLocation());
        jobOffer.setJobType(JobType.valueOf(request.getJobType()));
        jobOffer.setExperienceLevel(ExperienceLevel.valueOf(request.getExperienceLevel()));
        jobOffer.setDeadline(request.getDeadline());

        jobOfferRepository.save(jobOffer);
        return JobOfferMapper.toResponse(jobOffer);
    }

    public JobOfferResponse closeOffer(Long id) {
        Long companyId = SecurityUtils.getCurrentUserId();

        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!jobOffer.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette offre");
        }

        jobOffer.setStatus(JobOfferStatus.CLOSED);
        jobOfferRepository.save(jobOffer);
        return JobOfferMapper.toResponse(jobOffer);
    }

    public void delete(Long id) {
        Long companyId = SecurityUtils.getCurrentUserId();

        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!jobOffer.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette offre");
        }

        jobOfferRepository.deleteById(id);
    }
}
