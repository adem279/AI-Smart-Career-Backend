package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.ResumeResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Resume;
import com.smartcareer.aismartcareerbackend.mapper.ResumeMapper;
import com.smartcareer.aismartcareerbackend.repository.CandidateRepository;
import com.smartcareer.aismartcareerbackend.repository.ResumeRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateRepository candidateRepository;
    private final FileStorageService fileStorageService;

    public ResumeResponse upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            throw new RuntimeException("Seuls les fichiers PDF ou Word sont acceptés");
        }

        Long candidateId = SecurityUtils.getCurrentUserId();
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        String filePath = fileStorageService.storeFile(file);

        Resume resume = Resume.builder()
                .fileName(file.getOriginalFilename())
                .filePath(filePath)
                .candidate(candidate)
                .build();

        resumeRepository.save(resume);
        return ResumeMapper.toResponse(resume);
    }

    public List<ResumeResponse> getMyResumes() {
        Long candidateId = SecurityUtils.getCurrentUserId();
        return resumeRepository.findByCandidateId(candidateId)
                .stream()
                .map(ResumeMapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        Long candidateId = SecurityUtils.getCurrentUserId();

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV introuvable"));

        if (!resume.getCandidate().getId().equals(candidateId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce CV");
        }

        fileStorageService.deleteFile(resume.getFilePath());
        resumeRepository.deleteById(id);
    }
}