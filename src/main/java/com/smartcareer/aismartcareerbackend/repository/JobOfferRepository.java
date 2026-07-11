package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.JobOffer;
import com.smartcareer.aismartcareerbackend.entities.JobOfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByCompanyId(Long companyId);
    List<JobOffer> findByStatus(JobOfferStatus status);
}
