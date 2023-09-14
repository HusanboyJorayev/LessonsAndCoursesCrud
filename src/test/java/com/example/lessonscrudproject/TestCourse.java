package com.example.lessonscrudproject;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.model.Courses;
import com.example.lessonscrudproject.repository.CoursesRepository;
import com.example.lessonscrudproject.security.CoursesService;
import com.example.lessonscrudproject.security.mapper.CoursesMapper;
import com.example.lessonscrudproject.security.validation.CoursesValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCourse {
    private CoursesService coursesService;
    private CoursesMapper coursesMapper;
    private CoursesRepository coursesRepository;
    private CoursesValidation coursesValidation;

    @BeforeEach
    void initObject() {
        this.coursesMapper = mock(CoursesMapper.class);
        this.coursesRepository = mock(CoursesRepository.class);
        this.coursesValidation = mock(CoursesValidation.class);
        this.coursesService = new CoursesService(coursesMapper, coursesRepository, coursesValidation);
    }

    @Test
    void createValidation() {
        when(this.coursesValidation.validate(any()))
                .thenReturn(List.of(new ErrorDto("Validation error", "validation")));

        ResponseDto<CoursesDto> response = this.coursesService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -3);
        Assertions.assertNull(response.getData());

        verify(this.coursesValidation, times(1)).validate(any());
    }

    @Test
    void createPositive() {
        CoursesDto dto = CoursesDto.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        Courses courses = Courses.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        when(this.coursesMapper.toEntity(any()))
                .thenReturn(courses);
        when(this.coursesMapper.toDto(any()))
                .thenReturn(dto);

        ResponseDto<CoursesDto> response = this.coursesService.create(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertNotNull(response.getData());

        verify(this.coursesMapper, times(1)).toDto(any());
        verify(this.coursesMapper, times(1)).toEntity(any());

    }

    @Test
    void createException() {
        when(this.coursesMapper.toEntity(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<CoursesDto> response = this.coursesService.create(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getPositive() {
        CoursesDto dto = CoursesDto.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        Courses courses = Courses.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        when(this.coursesMapper.toDtoWithLessons(any()))
                .thenReturn(dto);

        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(courses));

        ResponseDto<CoursesDto> response = this.coursesService.get(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);

        verify(this.coursesMapper, times(1)).toDtoWithLessons(any());
        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CoursesDto> response = this.coursesService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        CoursesDto coursesDto = new CoursesDto();

        when(this.coursesValidation.validate(any()))
                .thenReturn(List.of(new ErrorDto("Validation error", "validation")));

        ResponseDto<CoursesDto> response = this.coursesService.update(coursesDto, any());
        Assertions.assertEquals(response.getCode(), -3);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());

        verify(this.coursesValidation, times(1)).validate(any());
    }

    @Test
    void updatePositive() {
        CoursesDto dto = CoursesDto.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        Courses courses = Courses.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        when(this.coursesMapper.toDtoWithLessons(any()))
                .thenReturn(dto);

        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(courses));


        ResponseDto<CoursesDto> response = this.coursesService.update(dto, any());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.coursesMapper, times(1)).toDtoWithLessons(any());
    }

    @Test
    void updateNegative() {
        CoursesDto coursesDto = new CoursesDto();

        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CoursesDto> response = this.coursesService.update(coursesDto, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertNull(response.getData());

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateException() {
        CoursesDto coursesDto = new CoursesDto();
        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<CoursesDto> response = this.coursesService.update(coursesDto, any());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        CoursesDto dto = CoursesDto.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        Courses courses = Courses.builder()
                .id(1)
                .name("Java Backand")
                .type("IT")
                .build();

        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(courses));

        when(this.coursesMapper.toDto(any()))
                .thenReturn(dto);

        ResponseDto<CoursesDto> response = this.coursesService.delete(any());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertTrue(response.isSuccess());

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
        verify(this.coursesMapper, times(1)).toDto(any());
    }

    @Test
    void deleteNegative() {
        when(this.coursesRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<CoursesDto> response = this.coursesService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.coursesRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }
}
