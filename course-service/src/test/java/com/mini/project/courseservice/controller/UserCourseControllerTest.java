package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.UserCourseController;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.dto.UserCourseDto;
import com.mini.project.courseservice.dto.UserCourseRequestDto;
import com.mini.project.courseservice.service.UserCourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCourseController.class)
class UserCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserCourseService userCourseService;

    @Test
    void testCreateUserCourse() throws Exception {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder()
                .userId(1L)
                .courseId(1L)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePremiumUserCourse() throws Exception {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder()
                .userId(1L)
                .courseId(1L)
                .build();

        UserCourseDto responseDto = UserCourseDto.builder()
                .userId(1L)
                .courses(CoursedDto.builder().id(1L).build())
                .build();
        Mockito.when(userCourseService.createPremium(any(UserCourseRequestDto.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user-courses/premium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }
}