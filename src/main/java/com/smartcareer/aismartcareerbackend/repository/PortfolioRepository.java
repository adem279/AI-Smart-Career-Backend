package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByCandidateId(Long candidateId);
}
