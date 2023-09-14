package com.example.lessonscrudproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterConfirmDto {
    @NotBlank(message = "username cannot be null or empty")
    private String username;
    @NotBlank(message = "code cannot be null or empty")
    private String code;
}
