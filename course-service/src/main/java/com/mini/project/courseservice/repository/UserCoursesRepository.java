package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.UsersCourses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoursesRepository extends JpaRepository<UsersCourses, Long> {

  boolean existsByCoursesIdAndUserId(Long courseId, Long userId);
}