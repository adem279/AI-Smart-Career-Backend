package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.SkillRequest;
import com.smartcareer.aismartcareerbackend.dto.SkillResponse;
import com.smartcareer.aismartcareerbackend.entities.Skill;
import com.smartcareer.aismartcareerbackend.mapper.SkillMapper;
import com.smartcareer.aismartcareerbackend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillResponse create(SkillRequest request) {
        if (skillRepository.existsByName(request.getName())) {
            throw new RuntimeException("Cette compétence existe déjà");
        }

        Skill skill = Skill.builder()
                .name(request.getName())
                .build();

        skillRepository.save(skill);
        return SkillMapper.toResponse(skill);
    }

    public List<SkillResponse> getAll() {
        return skillRepository.findAll()
                .stream()
                .map(SkillMapper::toResponse)
                .toList();
    }

    public SkillResponse getById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));
        return SkillMapper.toResponse(skill);
    }

    public SkillResponse update(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));

        skill.setName(request.getName());
        skillRepository.save(skill);
        return SkillMapper.toResponse(skill);
    }

    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Compétence introuvable");
        }
        skillRepository.deleteById(id);
    }
}
