package com.example.lessonscrudproject.security.mapper;



import com.example.lessonscrudproject.dto.AuthDto;
import com.example.lessonscrudproject.model.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class AuthMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected AuthoritiesMapper authoritiesMapper;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(auth.getPassword()))")
    public abstract AuthDto toDto(Auth auth);

    @Mapping(ignore = true, target = "authId")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "code", expression = "java(String.valueOf(0000))")
    public abstract Auth toEntity(AuthDto authDto);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(auth.getPassword()))")
    @Mapping(target = "authorities", expression = "java(auth.getAuthorities().stream().map(this.authoritiesMapper::toDto).collect(Collectors.toSet()))")
    public abstract AuthDto toDtoWithAuthorities(Auth auth);

    public void view(Auth auth) {
        AuthDto dto = new AuthDto();
        passwordEncoder.encode(auth.getPassword());
        String str=String.valueOf(0000);
        dto.setAuthorities(auth.getAuthorities().stream().map(this.authoritiesMapper::toDto).collect(Collectors.toSet()));
    }
}
