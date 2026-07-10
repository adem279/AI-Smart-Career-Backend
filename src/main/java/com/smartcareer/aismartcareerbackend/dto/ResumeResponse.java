package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResumeResponse {

    private Long id;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;
}
