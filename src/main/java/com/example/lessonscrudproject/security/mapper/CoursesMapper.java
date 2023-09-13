package com.example.lessonscrudproject.security.mapper;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.model.Courses;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class CoursesMapper {
    @Autowired
    protected LessonsMapper lessonsMapper;

    @Mapping(ignore = true,target = "lessons")
    public abstract CoursesDto toDto(Courses courses);

    @Mapping(ignore = true,target = "id")
    @Mapping(ignore = true,target = "createdAt")
    @Mapping(ignore = true,target = "updatedAt")
    @Mapping(ignore = true,target = "deletedAt")
    @Mapping(target = "perWeek",expression = "java(3)")
    @Mapping(ignore = true,target = "lessons")
    public abstract Courses toEntity(CoursesDto dto);

    @Mapping(target = "perWeek",expression = "java(3)")
    @Mapping(target = "lessons",expression = "java(courses.getLessons().stream().map(this.lessonsMapper::toDto).collect(Collectors.toSet()))")
    public abstract CoursesDto toDtoWithLessons(Courses courses);


    @Mapping(ignore = true,target = "lessons")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(CoursesDto dto,@MappingTarget Courses courses);


    public void view(Courses courses){
        CoursesDto dto=new CoursesDto();
        dto.setLessons(courses.getLessons().stream().map(this.lessonsMapper::toDto).collect(Collectors.toSet()));
    }
}
