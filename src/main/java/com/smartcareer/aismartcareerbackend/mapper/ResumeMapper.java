package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.ResumeResponse;
import com.smartcareer.aismartcareerbackend.entities.Resume;

public class ResumeMapper {

    public static ResumeResponse toResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .filePath(resume.getFilePath())
                .uploadDate(resume.getUploadDate())
                .build();
    }
}