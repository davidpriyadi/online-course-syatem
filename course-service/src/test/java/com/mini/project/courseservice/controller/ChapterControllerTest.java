package com.mini.project.courseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.project.courseservice.controllers.ChapterController;
import com.mini.project.courseservice.dto.ChapterDto;
import com.mini.project.courseservice.dto.ChapterFilter;
import com.mini.project.courseservice.dto.ChapterRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.service.ChapterService;
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

@WebMvcTest(ChapterController.class)
public class ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChapterService chapterService;


    @Test
    public void testGetChapter() throws Exception {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setName("Test Chapter");
        Page<ChapterDto> page = new PageImpl<>(Collections.singletonList(chapterDto));
        Mockito.when(chapterService.getChapter(any(ChapterFilter.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/chapters")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChapterById() throws Exception {
        ChapterDto chapterDto = new ChapterDto();
        Mockito.when(chapterService.getChapterById(anyLong())).thenReturn(chapterDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/chapters/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testDeleteChapter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/chapters/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateChapter() throws Exception {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setName("Updated Chapter");
        chapterDto.setCourses(CoursedDto.builder().id(1L).build());
        Mockito.when(chapterService.updateChapter(anyLong(), any(ChapterRequestDto.class))).thenReturn(chapterDto);

        ChapterRequestDto chapterRequestDto = ChapterRequestDto.builder()
                .name("Updated Chapter")
                .coursesId(1L)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String chapterRequestJson = objectMapper.writeValueAsString(chapterRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/chapters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(chapterRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Updated Chapter\",\"courses\":{\"id\":1}}"));
    }
}