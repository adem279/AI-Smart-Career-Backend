package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByApplicationIdOrderBySentAtAsc(Long applicationId);

    long countByApplicationIdAndSenderIdNotAndIsReadFalse(Long applicationId, Long senderId);

    @Query("SELECT m FROM Message m WHERE m.application.id = :applicationId ORDER BY m.sentAt DESC LIMIT 1")
    Message findLastMessage(@Param("applicationId") Long applicationId);

    @Query("SELECT DISTINCT m.application.id FROM Message m WHERE m.application.candidate.id = :candidateId")
    List<Long> findApplicationIdsWithMessagesForCandidate(@Param("candidateId") Long candidateId);

    @Query("SELECT DISTINCT m.application.id FROM Message m WHERE m.application.jobOffer.company.id = :companyId")
    List<Long> findApplicationIdsWithMessagesForCompany(@Param("companyId") Long companyId);
}