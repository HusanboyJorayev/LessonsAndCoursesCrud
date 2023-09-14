package com.example.lessonscrudproject;


import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.model.Lessons;
import com.example.lessonscrudproject.repository.LessonsRepository;
import com.example.lessonscrudproject.security.LessonsService;
import com.example.lessonscrudproject.security.mapper.LessonsMapper;
import com.example.lessonscrudproject.security.validation.LessonsValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestLessons {
    private LessonsRepository lessonsRepository;
    private LessonsValidation lessonsValidation;
    private LessonsService lessonsService;
    private LessonsMapper lessonsMapper;

    @BeforeEach
    void initObject() {
        this.lessonsMapper = mock(LessonsMapper.class);
        this.lessonsRepository = mock(LessonsRepository.class);
        this.lessonsValidation = mock(LessonsValidation.class);
        this.lessonsService = new LessonsService(lessonsRepository, lessonsMapper, lessonsValidation);
    }

    @Test
    void createValidation() {
        when(this.lessonsValidation.validate(any()))
                .thenReturn(List.of(new ErrorDto("this is a validation error", "Validation")));

        ResponseDto<LessonsDto> response = this.lessonsService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -3);
        Assertions.assertNull(response.getData());

        verify(this.lessonsValidation, timeout(1)).validate(any());
    }

    @Test
    void createPositive() {
        LessonsDto lessonsDto = LessonsDto.builder()
                .content("good")
                .status(true)
                .build();

        Lessons lessons = Lessons.builder()
                .content("good")
                .status(true)
                .build();

        when(this.lessonsMapper.toDto(any()))
                .thenReturn(lessonsDto);

        when(this.lessonsMapper.toEntity(any()))
                .thenReturn(lessons);

        ResponseDto<LessonsDto> response = this.lessonsService.create(any());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());

        verify(this.lessonsMapper, times(1)).toDto(any());
        verify(this.lessonsMapper, times(1)).toEntity(any());
    }

    @Test
    void createException() {
        when(this.lessonsMapper.toEntity(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<LessonsDto> response = this.lessonsService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertNull(response.getData());

        verify(this.lessonsMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        LessonsDto lessonsDto = LessonsDto.builder()
                .content("good")
                .status(true)
                .build();

        Lessons lessons = Lessons.builder()
                .content("good")
                .status(true)
                .build();

        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(lessons));

        when(this.lessonsMapper.toDto(any()))
                .thenReturn(lessonsDto);

        ResponseDto<LessonsDto> response = this.lessonsService.get(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(response.getCode(), 0);


        verify(this.lessonsMapper, times(1)).toDto(any());
        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<LessonsDto> response = this.lessonsService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateValidation() {
        LessonsDto dto = new LessonsDto();
        when(this.lessonsValidation.validate(any()))
                .thenReturn(List.of(new ErrorDto("this is a validation error", "Validation")));

        ResponseDto<LessonsDto> response = this.lessonsService.update(dto, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -3);
        Assertions.assertNull(response.getData());

        verify(this.lessonsValidation, timeout(1)).validate(any());
    }

    @Test
    void updatePositive() {
        LessonsDto lessonsDto = LessonsDto.builder()
                .content("good")
                .status(true)
                .build();

        Lessons lessons = Lessons.builder()
                .content("good")
                .status(true)
                .build();

        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(lessons));
        when(this.lessonsMapper.toDto(any()))
                .thenReturn(lessonsDto);

        ResponseDto<LessonsDto> response = this.lessonsService.update(lessonsDto, any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);

        verify(this.lessonsMapper, times(1)).toDto(any());
        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateNegative() {
        LessonsDto dto = new LessonsDto();
        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<LessonsDto> response = this.lessonsService.update(dto, any());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void updateException() {
        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<LessonsDto> response = this.lessonsService.update(any(), 1);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertNull(response.getData());

        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        LessonsDto lessonsDto = LessonsDto.builder()
                .content("good")
                .status(true)
                .build();

        Lessons lessons = Lessons.builder()
                .content("good")
                .status(true)
                .build();

        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(lessons));
        when(this.lessonsMapper.toDto(any()))
                .thenReturn(lessonsDto);

        ResponseDto<LessonsDto> response = this.lessonsService.delete(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);

        verify(this.lessonsMapper, times(1)).toDto(any());
        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void deleteNegative() {
        when(this.lessonsRepository.findByIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<LessonsDto> response = this.lessonsService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.lessonsRepository, times(1)).findByIdAndDeletedAtIsNull(any());
    }
}
