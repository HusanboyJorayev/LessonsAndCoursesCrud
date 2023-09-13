package com.example.lessonscrudproject.security.mapper;


import com.example.lessonscrudproject.dto.AuthoritiesDto;
import com.example.lessonscrudproject.model.Authorities;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class AuthoritiesMapper {

    public abstract AuthoritiesDto toDto(Authorities authorities);

    @Mapping(ignore = true, target = "authoritiesId")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    public abstract Authorities toEntity(AuthoritiesDto dto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(AuthoritiesDto dto, @MappingTarget Authorities authorities);
}
