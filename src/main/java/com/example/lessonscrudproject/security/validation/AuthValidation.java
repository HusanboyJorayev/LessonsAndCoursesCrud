package com.example.lessonscrudproject.security.validation;



import com.example.lessonscrudproject.dto.AuthDto;
import com.example.lessonscrudproject.dto.ErrorDto;
import com.example.lessonscrudproject.repository.AuthRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthValidation {
    private final AuthRepository authRepository;

    public List<ErrorDto>validate(AuthDto dto){
        List<ErrorDto>errors=new ArrayList<>();
        if (this.authRepository.existsByUsernameAndEnabledIsTrueAndDeletedAtIsNull(dto.getUsername())){
            errors.add(new ErrorDto(String.format("Auth with username %S: is already exist",dto.getUsername()),"Username"));
        }
        if (StringUtils.isBlank(dto.getName())){
            errors.add(new ErrorDto("name cannot be null or empty","name"));
        }
        if (StringUtils.isBlank(dto.getSurname())){
            errors.add(new ErrorDto("surname cannot be null or empty","surname"));
        }
        if (StringUtils.isBlank(dto.getPassword())){
            errors.add(new ErrorDto("password cannot be null or empty","password"));
        }
        return errors;
    }
}
