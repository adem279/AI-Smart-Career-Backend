package com.smartcareer.aismartcareerbackend.repository;

import com.smartcareer.aismartcareerbackend.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
}
