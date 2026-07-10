package com.smartcareer.aismartcareerbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double salary;

    private String location;

    private String jobType; // "CDI", "CDD", "Stage", "Freelance"...

    private String experienceLevel; // "Junior", "Confirmé", "Senior"...

    private LocalDate deadline;

    @Builder.Default
    private String status = "OPEN"; // "OPEN", "CLOSED"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "jobOffer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> applications;
}
