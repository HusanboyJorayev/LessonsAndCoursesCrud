package com.example.lessonscrudproject.controller;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.security.CoursesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "course")
public class CoursesController implements SimpleCrud<Integer, CoursesDto> {
    private final CoursesService coursesService;

    @Override
    @PostMapping(value = "/create")
    public ResponseDto<CoursesDto> create(@RequestBody @Valid CoursesDto dto) {
        return this.coursesService.create(dto);
    }

    @Override
    @GetMapping(value = "/get")
    public ResponseDto<CoursesDto> get(@RequestParam Integer id) {
        return this.coursesService.get(id);
    }

    @Override
    @PutMapping(value = "/update{id}")
    public ResponseDto<CoursesDto> update(@RequestBody @Valid CoursesDto dto, @PathVariable Integer id) {
        return this.coursesService.update(dto, id);
    }

    @Override
    @DeleteMapping(value = "/delete")
    public ResponseDto<CoursesDto> delete(@RequestParam Integer id) {
        return this.coursesService.delete(id);
    }

}
