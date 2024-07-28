package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.MentorDto;
import com.mini.project.courseservice.dto.MentorRequestDto;
import com.mini.project.courseservice.dto.mapper.MentorsMapper;
import com.mini.project.courseservice.entity.Mentors;
import com.mini.project.courseservice.repository.MentorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorServiceTest {

    @Mock
    private MentorsRepository mentorsRepository;

    @Mock
    private MentorsMapper mentorsMapper;

    @InjectMocks
    private MentorService mentorService;

    @Test
    void testCreateMentor() {
        MentorRequestDto mentorRequestDto = MentorRequestDto.builder().build();
        Mentors mentor = Mentors.builder().build();
        MentorDto mentorDto = MentorDto.builder().build();

        when(mentorsMapper.toCreateEntity(any(MentorRequestDto.class))).thenReturn(mentor);
        when(mentorsRepository.save(any(Mentors.class))).thenReturn(mentor);
        when(mentorsMapper.toDto(any(Mentors.class))).thenReturn(mentorDto);

        MentorDto result = mentorService.createMentor(mentorRequestDto);

        assertNotNull(result);
        verify(mentorsRepository, times(1)).save(any(Mentors.class));
    }

    @Test
    void testGetMentor() {
        Mentors mentor = Mentors.builder().build();
        MentorDto mentorDto = MentorDto.builder().build();

        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.of(mentor));
        when(mentorsMapper.toDto(any(Mentors.class))).thenReturn(mentorDto);

        MentorDto result = mentorService.getMentor(1L);

        assertNotNull(result);
        verify(mentorsRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateMentor() {
        MentorRequestDto mentorRequestDto = MentorRequestDto.builder().build();
        Mentors mentor = Mentors.builder().build();
        MentorDto mentorDto = MentorDto.builder().build();

        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.of(mentor));
        when(mentorsRepository.save(any(Mentors.class))).thenReturn(mentor);
        when(mentorsMapper.toDto(any(Mentors.class))).thenReturn(mentorDto);

        MentorDto result = mentorService.updateMentor(1L, mentorRequestDto);

        assertNotNull(result);
        verify(mentorsRepository, times(1)).findById(anyLong());
        verify(mentorsRepository, times(1)).save(any(Mentors.class));
    }

    @Test
    void testDeleteMentor() {
        Mentors mentor = Mentors.builder().build();

        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.of(mentor));
        doNothing().when(mentorsRepository).delete(any(Mentors.class));

        mentorService.deleteMentor(1L);

        verify(mentorsRepository, times(1)).findById(anyLong());
        verify(mentorsRepository, times(1)).delete(any(Mentors.class));

        // Scenario where mentor is not found
        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> mentorService.deleteMentor(1L));
        verify(mentorsRepository, times(2)).findById(anyLong()); // Called twice, once for each scenario
    }
}