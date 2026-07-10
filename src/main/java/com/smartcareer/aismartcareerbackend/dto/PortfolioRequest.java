package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PortfolioRequest {

    @NotBlank private String title;
    private String description;
    private String projectUrl;
    private String githubUrl;
}
