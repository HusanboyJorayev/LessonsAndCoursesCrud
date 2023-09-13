package com.example.lessonscrudproject.dto;

import com.example.lessonscrudproject.model.Lessons;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursesDto {
    private Integer id;
    private String name;
    private String type;
    private Integer perWeek;
    private Integer durationMonth;
    private Integer durationDays;
    private Integer durationHours;

    private Set<LessonsDto> lessons;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
