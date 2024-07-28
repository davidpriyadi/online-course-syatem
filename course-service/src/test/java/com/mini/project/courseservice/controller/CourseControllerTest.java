package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.CourseController;
import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.service.CourseService;
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

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void testCreateCourse() throws Exception {
        CourseRequestDto courseRequestDto = CourseRequestDto.builder()
                .name("New Course")
                .type("free")
                .level("beginner")
                .status("published")
                .thumbnail("http://Thumbnail")
                .description("Course description")
                .price(100)
                .certificate(true)
                .build();

        CoursedDto coursedDto = CoursedDto.builder().name("New Course").build();
        Mockito.when(courseService.createCourse(any(CourseRequestDto.class))).thenReturn(coursedDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String courseRequestJson = objectMapper.writeValueAsString(courseRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"New Course\"}"));
    }

    @Test
    void testGetCourse() throws Exception {
        CoursedDto coursedDto = CoursedDto.builder()
                .name("Test Course")
                .type("free")
                .level("beginner")
                .status("published")
                .thumbnail("Thumbnail")
                .build();
        Page<CoursedDto> page = new PageImpl<>(Collections.singletonList(coursedDto));
        Mockito.when(courseService.getCourse(any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        CoursedDto coursedDto = CoursedDto.builder().build();
        Mockito.when(courseService.getById(anyLong())).thenReturn(coursedDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void testUpdateCourse() throws Exception {
        CoursedDto coursedDto = CoursedDto.builder().name("Updated Course").build();
        Mockito.when(courseService.updateCourse(anyLong(), any(CourseRequestDto.class))).thenReturn(coursedDto);

        CourseRequestDto courseRequestDto = CourseRequestDto.builder()
                .name("Updated Course")
                .type("free")
                .level("beginner")
                .status("published")
                .thumbnail("http://Thumbnail")
                .description("Updated description")
                .price(200)
                .certificate(true)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String courseRequestJson = objectMapper.writeValueAsString(courseRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Updated Course\"}"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }
}