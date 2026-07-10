package com.smartcareer.aismartcareerbackend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CandidateSkillResponse {

    private Long id;
    private Long skillId;
    private String skillName;
    private String level;
}
