package com.example.lessonscrudproject.security;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.model.Courses;
import com.example.lessonscrudproject.repository.CoursesRepository;
import com.example.lessonscrudproject.security.mapper.CoursesMapper;
import com.example.lessonscrudproject.security.validation.CoursesValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesService implements SimpleCrud<Integer, CoursesDto> {
    private final CoursesMapper coursesMapper;
    private final CoursesRepository coursesRepository;
    private final CoursesValidation coursesValidation;

    @Override
    public ResponseDto<CoursesDto> create(CoursesDto dto) {
        List<ErrorDto> errors = this.coursesValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<CoursesDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .errors(errors)
                    .build();
        }
        try {
            Courses courses = this.coursesMapper.toEntity(dto);
            courses.setCreatedAt(LocalDateTime.now());
            this.coursesRepository.save(courses);
            return ResponseDto.<CoursesDto>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.coursesMapper.toDto(courses))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CoursesDto>builder()
                    .code(-1)
                    .message("user while saving error")
                    .build();
        }
    }


    @Override
    public ResponseDto<CoursesDto> get(Integer id) {
        return this.coursesRepository.findByIdAndDeletedAtIsNull(id)
                .map(course -> ResponseDto.<CoursesDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(this.coursesMapper.toDtoWithLessons(course))
                        .build())
                .orElse(ResponseDto.<CoursesDto>builder()
                        .code(-1)
                        .message("user is not found")
                        .build());
    }

    @Override
    public ResponseDto<CoursesDto> update(CoursesDto dto, Integer id) {
        List<ErrorDto> errors = this.coursesValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<CoursesDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .errors(errors)
                    .build();
        }
        try {
            return this.coursesRepository.findByIdAndDeletedAtIsNull(id)
                    .map(course -> {
                        course.setUpdatedAt(LocalDateTime.now());
                        this.coursesMapper.update(dto, course);
                        this.coursesRepository.save(course);
                        return ResponseDto.<CoursesDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.coursesMapper.toDtoWithLessons(course))
                                .build();
                    })
                    .orElse(ResponseDto.<CoursesDto>builder()
                            .code(-1)
                            .message("user is not found")
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CoursesDto>builder()
                    .code(-1)
                    .message("user while updating error")
                    .build();
        }
    }

    @Override
    public ResponseDto<CoursesDto> delete(Integer id) {
        return this.coursesRepository.findByIdAndDeletedAtIsNull(id)
                .map(course -> {
                    course.setDeletedAt(LocalDateTime.now());
                    this.coursesRepository.save(course);
                    return ResponseDto.<CoursesDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.coursesMapper.toDto(course))
                            .build();
                })
                .orElse(ResponseDto.<CoursesDto>builder()
                        .code(-1)
                        .message("user is not found")
                        .build());
    }
    public ResponseDto<Page<CoursesDto>> getByPage(Integer page, Integer size){
        Page<Courses>coursesPage=this.coursesRepository.findAllByDeletedAtIsNull(PageRequest.of(page,size));
        if (coursesPage.isEmpty()){
            return ResponseDto.<Page<CoursesDto>>builder()
                    .code(-1)
                    .message("Courses are not found")
                    .build();
        }
        return ResponseDto.<Page<CoursesDto>>builder()
                .success(true)
                .message("Ok")
                .data(coursesPage.map(this.coursesMapper::toDtoWithLessons))
                .build();
    }

}
