package com.example.lessonscrudproject.security.validation;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CoursesValidation {

    public List<ErrorDto>validate(CoursesDto dto){

        List<ErrorDto>errors=new ArrayList<>();

        if (StringUtils.isBlank(dto.getName())){
            errors.add(new ErrorDto("name cannot be null or empty","name"));
        }
        return errors;
    }
}
