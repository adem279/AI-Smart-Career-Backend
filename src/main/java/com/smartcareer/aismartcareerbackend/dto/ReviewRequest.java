package com.smartcareer.aismartcareerbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequest {

    @NotNull private Long candidateId;
    @NotNull @Min(1) @Max(5) private Integer rating;
    private String comment;
}
