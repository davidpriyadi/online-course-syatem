package com.mini.project.courseservice.service;

import com.mini.project.courseservice.client.UserClient;
import com.mini.project.courseservice.client.dto.UsersDto;
import com.mini.project.courseservice.dto.ReviewsDto;
import com.mini.project.courseservice.dto.ReviewsRequestDto;
import com.mini.project.courseservice.dto.mapper.ReviewsMapper;
import com.mini.project.courseservice.entity.Courses;
import com.mini.project.courseservice.entity.Reviews;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.ReviewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewsRepository reviewsRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @Mock
    private ReviewsMapper reviewsMapper;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testCreate() {
        ReviewsRequestDto requestDto = ReviewsRequestDto.builder().courseId(1L).userId(1L).build();
        Reviews review = Reviews.builder().build();
        Courses course = Courses.builder().build();
        ReviewsDto reviewsDto = ReviewsDto.builder().build();

        when(reviewsMapper.toReviews(any(ReviewsRequestDto.class))).thenReturn(review);
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn( UsersDto.builder().id(1l).build());
        when(reviewsRepository.existsByUserIdAndCourses_Id(anyLong(), anyLong())).thenReturn(false);
        when(reviewsRepository.save(any(Reviews.class))).thenReturn(review);
        when(reviewsMapper.toReviewsDto(any(Reviews.class))).thenReturn(reviewsDto);

        ReviewsDto result = reviewService.create(requestDto);

        assertNotNull(result);
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(reviewsRepository, times(1)).existsByUserIdAndCourses_Id(anyLong(), anyLong());
        verify(reviewsRepository, times(1)).save(any(Reviews.class));
    }

    @Test
    void testCreateCourseNotFound() {
        ReviewsRequestDto requestDto = ReviewsRequestDto.builder().courseId(1L).userId(1L).build();

        when(coursesRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.create(requestDto));
        verify(coursesRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateUserNotFound() {
        ReviewsRequestDto requestDto = ReviewsRequestDto.builder().courseId(1L).userId(1L).build();
        Reviews review = Reviews.builder().build();
        Courses course = Courses.builder().build();

        when(reviewsMapper.toReviews(any(ReviewsRequestDto.class))).thenReturn(review);
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> reviewService.create(requestDto));
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
    }

    @Test
    void testCreateReviewAlreadyExists() {
        ReviewsRequestDto requestDto = ReviewsRequestDto.builder().courseId(1L).userId(1L).build();
        Reviews review = Reviews.builder().build();
        Courses course = Courses.builder().build();

        when(reviewsMapper.toReviews(any(ReviewsRequestDto.class))).thenReturn(review);
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn(UsersDto.builder().id(1L).build());
        when(reviewsRepository.existsByUserIdAndCourses_Id(anyLong(), anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> reviewService.create(requestDto));
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(reviewsRepository, times(1)).existsByUserIdAndCourses_Id(anyLong(), anyLong());
    }

    @Test
    void testDelete() {
        Reviews review = Reviews.builder().build();

        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.of(review));
        doNothing().when(reviewsRepository).delete(any(Reviews.class));

        reviewService.delete(1L);

        verify(reviewsRepository, times(1)).findById(anyLong());
        verify(reviewsRepository, times(1)).delete(any(Reviews.class));
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.delete(1L));
        verify(reviewsRepository, times(1)).findById(anyLong());
    }
}