package com.mini.project.courseservice.service;

import com.mini.project.courseservice.client.UserClient;
import com.mini.project.courseservice.dto.ReviewsDto;
import com.mini.project.courseservice.dto.ReviewsRequestDto;
import com.mini.project.courseservice.dto.mapper.ReviewsMapper;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewsRepository reviewsRepository;
    private final CoursesRepository coursesRepository;
    private final ReviewsMapper reviewsMapper;
    private final UserClient userClient;

    public ReviewsDto create(ReviewsRequestDto requestDto) {
        var review = reviewsMapper.toReviews(requestDto);

        //bisa di bikin validasi sebeneranya menggunakan validator
        var course = coursesRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        //bisa di bikin validasi sebeneranya menggunakan validator
        try {
            var user = userClient.getUserById(requestDto.getUserId());
            review.setUserId(user.getId());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        //bisa di bikin validasi sebeneranya menggunakan validator
        var exists = reviewsRepository.existsByUserIdAndCourses_Id(requestDto.getUserId(), requestDto.getCourseId());

        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review already exists");
        }


        review.setCourses(course);
        var savedReview = reviewsRepository.save(review);
        return reviewsMapper.toReviewsDto(savedReview);
    }

    public void delete(Long id) {
        var review = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewsRepository.delete(review);
    }
}
