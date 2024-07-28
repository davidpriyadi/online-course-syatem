package com.mini.project.courseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean certificate;

    private String thumbnail;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String level;

    private String description;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentors mentors;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapters> chapters;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersCourses> usersCours;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews;
}