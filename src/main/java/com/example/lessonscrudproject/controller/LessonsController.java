package com.example.lessonscrudproject.controller;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.security.LessonsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lesson")
public class LessonsController implements SimpleCrud<Integer, LessonsDto> {
    private final LessonsService lessonsService;

    @Override
    @PostMapping(value = "/create")
    public ResponseDto<LessonsDto> create(@RequestBody @Valid LessonsDto dto) {
        return this.lessonsService.create(dto);
    }

    @Override
    @GetMapping(value = "/get")
    public ResponseDto<LessonsDto> get(@RequestParam Integer id) {
        return this.lessonsService.get(id);
    }

    @Override
    @PutMapping(value = "/update{id}")
    public ResponseDto<LessonsDto> update(@RequestBody @Valid LessonsDto dto, @PathVariable Integer id) {
        return this.lessonsService.update(dto, id);
    }

    @Override
    @DeleteMapping(value = "/delete")
    public ResponseDto<LessonsDto> delete(@RequestParam Integer id) {
        return this.lessonsService.delete(id);
    }
}
