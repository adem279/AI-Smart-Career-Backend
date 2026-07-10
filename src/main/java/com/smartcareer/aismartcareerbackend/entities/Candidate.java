package com.smartcareer.aismartcareerbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidates")
@DiscriminatorValue("CANDIDATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Candidate extends User {

    private String address;

    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String photo;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CandidateSkill> candidateSkills;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Education> educations;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Experience> experiences;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Resume> resumes;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Application> applications;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviewsReceived;
}