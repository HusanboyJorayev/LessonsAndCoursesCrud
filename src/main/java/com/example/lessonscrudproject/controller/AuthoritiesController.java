package com.example.lessonscrudproject.controller;


import com.example.lessonscrudproject.dto.AuthoritiesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.security.AuthoritiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "authorities")
public class AuthoritiesController /*implements SimpleCrud<Integer, AuthoritiesDto>*/ {

    private final AuthoritiesService authoritiesService;


    @PostMapping(value = "/")
    public ResponseDto<AuthoritiesDto> create(@RequestBody AuthoritiesDto dto) {
        return this.authoritiesService.create(dto);
    }


    @GetMapping(value = "/{u}")
    public ResponseDto<AuthoritiesDto> get(@PathVariable(value = "u") String username) {
        return this.authoritiesService.get(username);
    }


    @PutMapping(value = "/{u}")
    public ResponseDto<AuthoritiesDto> update(@RequestBody AuthoritiesDto dto, @PathVariable(value = "u") String username) {
        return this.authoritiesService.update(dto, username);
    }


    @DeleteMapping(value = "/{u}")
    public ResponseDto<AuthoritiesDto> delete(@PathVariable(value = "u") String username) {
        return this.authoritiesService.delete(username);
    }


    @GetMapping(value = "/getAll")
    public ResponseDto<List<AuthoritiesDto>> getAll() {
        return this.authoritiesService.getAll();
    }
}
