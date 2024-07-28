package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.ReviewsDto;
import com.mini.project.courseservice.dto.ReviewsRequestDto;
import com.mini.project.courseservice.entity.Reviews;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewsMapper {
    ReviewsDto toReviewsDto(Reviews reviews);

    Reviews toReviews(ReviewsRequestDto reviewsRequestDto);

    void updateEntity(@MappingTarget Reviews reviews, ReviewsRequestDto reviewsRequestDto);
}
