package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.LessonsController;
import com.mini.project.courseservice.dto.LessonDto;
import com.mini.project.courseservice.dto.LessonFilterDto;
import com.mini.project.courseservice.dto.LessonRequestDto;
import com.mini.project.courseservice.service.LessonsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonsController.class)
class LessonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonsService lessonsService;

    @Test
    void testGetLesson() throws Exception {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setName("Test Lesson");
        Page<LessonDto> page = new PageImpl<>(Collections.singletonList(lessonDto));
        Mockito.when(lessonsService.getLesson(any(LessonFilterDto.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lessons")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLessonById() throws Exception {
        LessonDto lessonDto = new LessonDto();
        Mockito.when(lessonsService.getLessonById(anyLong())).thenReturn(lessonDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void testDeleteLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lessons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateLesson() throws Exception {
        LessonDto lessonDto = LessonDto.builder()
                .name("Updated Lesson")
                .build();
        Mockito.when(lessonsService.updateLesson(anyLong(), any(LessonRequestDto.class))).thenReturn(lessonDto);

        LessonRequestDto lessonRequestDto = LessonRequestDto.builder()
                .name("Updated Lesson")
                .chapterId(1L)
                .video("http://video")
                .duration(10)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String lessonRequestJson = objectMapper.writeValueAsString(lessonRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lessonRequestJson))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateLesson() throws Exception {
        LessonRequestDto lessonRequestDto = LessonRequestDto.builder()
                .name("New Lesson")
                .chapterId(1L)
                .video("http://video")
                .duration(10)
                .build();

        LessonDto lessonDto = new LessonDto();
        lessonDto.setName("New Lesson");
        Mockito.when(lessonsService.createLesson(any(LessonRequestDto.class))).thenReturn(lessonDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String lessonRequestJson = objectMapper.writeValueAsString(lessonRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lessonRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"New Lesson\"}"));
    }
}