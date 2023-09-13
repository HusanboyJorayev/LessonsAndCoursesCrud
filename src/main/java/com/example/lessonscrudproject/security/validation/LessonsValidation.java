package com.example.lessonscrudproject.security.validation;

import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.model.Courses;
import com.example.lessonscrudproject.repository.CoursesRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LessonsValidation {
    private final CoursesRepository coursesRepository;

    public List<ErrorDto> validate(LessonsDto dto) {
        List<ErrorDto> errors = new ArrayList<>();

        Optional<Courses> optional = this.coursesRepository.findByIdAndDeletedAtIsNull(dto.getCourseId());
        if (optional.isEmpty()) {
            errors.add(new ErrorDto("you cannot lesson becouse course is null", "lesson"));
        }
        if (StringUtils.isBlank(dto.getTitle())) {
            errors.add(new ErrorDto("title cannot be null or empty", "title"));
        }
        return errors;
    }
}
