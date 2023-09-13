package com.example.lessonscrudproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "name cannot be null or empty")
    private String name;
    private String type;
    private Integer perWeek;
    private Integer durationMonth;
    private Integer durationDays;
    private Integer durationHours;

    @OneToMany(mappedBy = "courseId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Lessons>lessons;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
