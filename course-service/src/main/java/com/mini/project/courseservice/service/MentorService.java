package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.MentorDto;
import com.mini.project.courseservice.dto.MentorRequestDto;
import com.mini.project.courseservice.dto.mapper.MentorsMapper;
import com.mini.project.courseservice.repository.MentorsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorsRepository mentorsRepository;
    private final MentorsMapper mentorsMapper;

    @CachePut(value = "mentors", key = "#result.id")
    public MentorDto createMentor(MentorRequestDto mentorDto) {
        var mentor = mentorsMapper.toCreateEntity(mentorDto);
        return mentorsMapper.toDto(mentorsRepository.save(mentor));
    }

    @Cacheable(value = "mentors", key = "#id")
    public MentorDto getMentor(Long id) {
        var mentor = mentorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));
        return mentorsMapper.toDto(mentor);
    }

    @CacheEvict(value = "mentors", key = "#id")
    @CachePut(value = "mentors", key = "#id")
    public MentorDto updateMentor(Long id, MentorRequestDto mentorDto) {
        var mentor = mentorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));
        mentorsMapper.updateEntity(mentor, mentorDto);
        return mentorsMapper.toDto(mentorsRepository.save(mentor));
    }

    @CacheEvict(value = "mentors", key = "#id")
    public void deleteMentor(Long id) {
        var mentor = mentorsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));
        mentorsRepository.delete(mentor);
    }
}
