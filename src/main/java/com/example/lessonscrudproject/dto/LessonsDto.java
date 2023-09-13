package com.example.lessonscrudproject.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonsDto {
    private Integer id;
    private Integer courseId;
    private  String title;
    private String content;
    private boolean status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
