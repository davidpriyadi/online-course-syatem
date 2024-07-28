package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.Chapters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChaptersRepository extends JpaRepository<Chapters, Long> , JpaSpecificationExecutor<Chapters> {
    boolean existsById(Long id);
}