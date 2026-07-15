package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByJobOfferId(Long jobOfferId);
    List<Application> findByJobOffer_Company_Id(Long companyId);
    boolean existsByCandidateIdAndJobOfferId(Long candidateId, Long jobOfferId);
}
