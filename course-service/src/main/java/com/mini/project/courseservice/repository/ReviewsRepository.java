package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> , JpaSpecificationExecutor<Reviews> {
    boolean existsByUserIdAndCourses_Id(Long userId, Long courseId);
}