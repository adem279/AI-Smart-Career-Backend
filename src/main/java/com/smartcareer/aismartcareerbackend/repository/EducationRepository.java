package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByCandidateId(Long candidateId);
}
