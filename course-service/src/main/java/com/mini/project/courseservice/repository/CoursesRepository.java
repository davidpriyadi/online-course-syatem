package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoursesRepository extends JpaRepository<Courses, Long> , JpaSpecificationExecutor<Courses> {
    boolean existsById(Long id);
}