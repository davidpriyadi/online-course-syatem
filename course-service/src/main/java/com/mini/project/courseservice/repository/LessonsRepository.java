package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LessonsRepository extends JpaRepository<Lessons, Long>, JpaSpecificationExecutor<Lessons> {
}