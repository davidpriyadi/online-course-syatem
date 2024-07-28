package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.MentorController;
import com.mini.project.courseservice.dto.MentorDto;
import com.mini.project.courseservice.dto.MentorRequestDto;
import com.mini.project.courseservice.repository.MentorsRepository;
import com.mini.project.courseservice.service.MentorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MentorController.class)
public class MentorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private MentorsRepository mentorsRepository;

    @Test
    public void testCreateMentor() throws Exception {
        MentorRequestDto mentorRequestDto = MentorRequestDto.builder()
                .name("New Mentor")
                .email("new@ymail.com")
                .profession("New Profession")
                .profile("New Profile")
                .build();

        MentorDto mentorDto = MentorDto.builder()
                .name("New Mentor")
                .build();
        Mockito.when(mentorService.createMentor(any(MentorRequestDto.class))).thenReturn(mentorDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String mentorRequestJson = objectMapper.writeValueAsString(mentorRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mentors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mentorRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"New Mentor\"}"));
    }

    @Test
    public void testGetMentor() throws Exception {
        MentorDto mentorDto = MentorDto.builder()
                .name("Test Mentor")
                .build();
        Mockito.when(mentorService.getMentor(anyLong())).thenReturn(mentorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/mentors/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Test Mentor\"}"));
    }

    @Test
    public void testUpdateMentor() throws Exception {
        MentorDto mentorDto = MentorDto.builder()
                .name("Updated Mentor")
                .build();
        Mockito.when(mentorService.updateMentor(anyLong(), any(MentorRequestDto.class))).thenReturn(mentorDto);

        MentorRequestDto mentorRequestDto = MentorRequestDto.builder()
                .name("New Mentor")
                .email("new@ymail.com")
                .profession("New Profession")
                .profile("New Profile")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String mentorRequestJson = objectMapper.writeValueAsString(mentorRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/mentors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mentorRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Updated Mentor\"}"));
    }

    @Test
    public void testDeleteMentor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/mentors/1"))
                .andExpect(status().isNoContent());
    }
}