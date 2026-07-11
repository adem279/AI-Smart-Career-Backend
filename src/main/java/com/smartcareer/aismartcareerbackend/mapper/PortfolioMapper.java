package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.PortfolioResponse;
import com.smartcareer.aismartcareerbackend.entities.Portfolio;

public class PortfolioMapper {

    public static PortfolioResponse toResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .description(portfolio.getDescription())
                .projectUrl(portfolio.getProjectUrl())
                .githubUrl(portfolio.getGithubUrl())
                .build();
    }
}