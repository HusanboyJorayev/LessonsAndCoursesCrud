package com.example.lessonscrudproject.security.mapper;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.model.Courses;
import com.example.lessonscrudproject.model.Lessons;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class LessonsMapper {


    public abstract LessonsDto toDto(Lessons lessons);

    @Mapping(ignore = true,target = "id")
    @Mapping(ignore = true,target = "createdAt")
    @Mapping(ignore = true,target = "updatedAt")
    @Mapping(ignore = true,target = "deletedAt")
    public abstract Lessons toEntity(LessonsDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(LessonsDto dto,@MappingTarget Lessons lessons);
}
