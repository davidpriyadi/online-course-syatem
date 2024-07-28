package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.ReviewController;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.dto.ReviewsDto;
import com.mini.project.courseservice.dto.ReviewsRequestDto;
import com.mini.project.courseservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    void testCreateReview() throws Exception {
        ReviewsRequestDto requestDto = ReviewsRequestDto.builder()
                .userId(1L)
                .courseId(1L)
                .rating(5)
                .note("Great course!")
                .build();

        ReviewsDto responseDto = ReviewsDto.builder()
                .userId(1L)
                .courses(CoursedDto.builder().id(1L).build())
                .rating(5)
                .note("Great course!")
                .build();
        Mockito.when(reviewService.create(any(ReviewsRequestDto.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"userId\":1,\"courses\":{\"id\":1},\"rating\":5,\"note\":\"Great course!\"}"));
    }

    @Test
    void testDeleteReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reviews/1"))
                .andExpect(status().isNoContent());
    }
}