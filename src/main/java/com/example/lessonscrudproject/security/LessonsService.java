package com.example.lessonscrudproject.security;

import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.model.Lessons;
import com.example.lessonscrudproject.repository.LessonsRepository;
import com.example.lessonscrudproject.security.mapper.LessonsMapper;
import com.example.lessonscrudproject.security.validation.LessonsValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonsService implements SimpleCrud<Integer, LessonsDto> {
    private final LessonsRepository lessonsRepository;
    private final LessonsMapper lessonsMapper;
    private final LessonsValidation lessonsValidation;

    @Override
    public ResponseDto<LessonsDto> create(LessonsDto dto) {
        List<ErrorDto> errors = this.lessonsValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<LessonsDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .errors(errors)
                    .build();
        }
        try {

            Lessons lessons = this.lessonsMapper.toEntity(dto);
            lessons.setCreatedAt(LocalDateTime.now());
            this.lessonsRepository.save(lessons);
            return ResponseDto.<LessonsDto>builder()
                    .message("Ok")
                    .success(true)
                    .data(this.lessonsMapper.toDto(lessons))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<LessonsDto>builder()
                    .code(-1)
                    .message("Card while saving error")
                    .build();
        }
    }

    @Override
    public ResponseDto<LessonsDto> get(Integer id) {
        return this.lessonsRepository.findByIdAndDeletedAtIsNull(id)
                .map(card -> ResponseDto.<LessonsDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(this.lessonsMapper.toDto(card))
                        .build())
                .orElse(ResponseDto.<LessonsDto>builder()
                        .code(-1)
                        .message("card is not found")
                        .build());
    }

    @Override
    public ResponseDto<LessonsDto> update(LessonsDto dto, Integer id) {
        List<ErrorDto> errors = this.lessonsValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<LessonsDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .errors(errors)
                    .build();
        }
        try {
            return this.lessonsRepository.findByIdAndDeletedAtIsNull(id)
                    .map(lesson -> {
                                lesson.setUpdatedAt(LocalDateTime.now());
                                this.lessonsMapper.update(dto, lesson);
                                this.lessonsRepository.save(lesson);
                                return ResponseDto.<LessonsDto>builder()
                                        .success(true)
                                        .message("Ok")
                                        .data(this.lessonsMapper.toDto(lesson))
                                        .build();
                            }
                    )
                    .orElse(ResponseDto.<LessonsDto>builder()
                            .code(-1)
                            .message("card is not found")
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LessonsDto>builder()
                    .code(-1)
                    .message("Card while updating error")
                    .build();
        }
    }

    @Override
    public ResponseDto<LessonsDto> delete(Integer id) {
        return this.lessonsRepository.findByIdAndDeletedAtIsNull(id)
                .map(lesson -> {
                    lesson.setDeletedAt(LocalDateTime.now());
                    this.lessonsRepository.save(lesson);
                    return ResponseDto.<LessonsDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.lessonsMapper.toDto(lesson))
                            .build();
                })
                .orElse(ResponseDto.<LessonsDto>builder()
                        .code(-1)
                        .message("card is not found")
                        .build());
    }
}
