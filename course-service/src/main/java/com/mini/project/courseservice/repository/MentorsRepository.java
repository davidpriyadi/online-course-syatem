package com.mini.project.courseservice.repository;

import com.mini.project.courseservice.entity.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorsRepository extends JpaRepository<Mentors, Long> {
    boolean existsUsersByEmail(String email);
}