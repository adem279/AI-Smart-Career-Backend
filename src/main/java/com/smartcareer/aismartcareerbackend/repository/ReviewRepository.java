package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCandidateId(Long candidateId);
    List<Review> findByCompanyId(Long companyId);
    boolean existsByCompanyIdAndCandidateId(Long companyId, Long candidateId);
}
