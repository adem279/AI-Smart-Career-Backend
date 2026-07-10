package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PortfolioResponse {

    private Long id;
    private String title;
    private String description;
    private String projectUrl;
    private String githubUrl;
}