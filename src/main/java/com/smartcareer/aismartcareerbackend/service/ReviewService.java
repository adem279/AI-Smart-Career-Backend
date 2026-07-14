package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.ReviewRequest;
import com.smartcareer.aismartcareerbackend.dto.ReviewResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Company;
import com.smartcareer.aismartcareerbackend.entities.Review;
import com.smartcareer.aismartcareerbackend.mapper.ReviewMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.CompanyRepository;
import com.smartcareer.aismartcareerbackend.repository.ReviewRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyRepository companyRepository;
    private final CandidateRepository candidateRepository;
    private final NotificationService notificationService;

    public ReviewResponse create(ReviewRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();

        if (reviewRepository.existsByCompanyIdAndCandidateId(companyId, request.getCandidateId())) {
            throw new RuntimeException("Vous avez déjà noté ce candidat");
        }

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .company(company)
                .candidate(candidate)
                .build();

        reviewRepository.save(review);

        notificationService.createNotification(
                candidate,
                "Nouvel avis reçu",
                company.getCompanyName() + " vous a laissé un avis (" + request.getRating() + "/5)"
        );

        return ReviewMapper.toResponse(review);
    }

    public List<ReviewResponse> getReviewsForCandidate(Long candidateId) {
        return reviewRepository.findByCandidateId(candidateId)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    public List<ReviewResponse> getMyReviewsWritten() {
        Long companyId = SecurityUtils.getCurrentUserId();
        return reviewRepository.findByCompanyId(companyId)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    public ReviewResponse update(Long id, ReviewRequest request) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis introuvable"));

        if (!review.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cet avis");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        reviewRepository.save(review);
        return ReviewMapper.toResponse(review);
    }

    public void delete(Long id) {
        Long companyId = SecurityUtils.getCurrentUserId();

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis introuvable"));

        if (!review.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cet avis");
        }

        reviewRepository.deleteById(id);
    }
}
