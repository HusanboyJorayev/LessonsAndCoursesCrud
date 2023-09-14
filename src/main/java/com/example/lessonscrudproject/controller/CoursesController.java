package com.example.lessonscrudproject.controller;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.security.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "course")
public class CoursesController implements SimpleCrud<Integer, CoursesDto> {
    private final CoursesService coursesService;

    @Override
    @PostMapping(value = "/create")
    @Operation(
            description = "this  method creates courses",
            method = "create",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            summary = "this is a create method"
    )
    public ResponseDto<CoursesDto> create(@RequestBody @Valid CoursesDto dto) {
        return this.coursesService.create(dto);
    }

    @Override
    @GetMapping(value = "/get")
    @Operation(
            description = "this  method gets courses",
            method = "get",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            summary = "this is a get method"
    )
    public ResponseDto<CoursesDto> get(@RequestParam Integer id) {
        return this.coursesService.get(id);
    }

    @Override
    @PutMapping(value = "/update{id}")
    @Operation(
            description = "this  method updates courses",
            method = "update",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            summary = "this is a update method"
    )
    public ResponseDto<CoursesDto> update(@RequestBody @Valid CoursesDto dto, @PathVariable Integer id) {
        return this.coursesService.update(dto, id);
    }

    @Override
    @DeleteMapping(value = "/delete")
    @Operation(
            description = "this  method deletes courses",
            method = "delete",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            summary = "this is a delete method"
    )
    public ResponseDto<CoursesDto> delete(@RequestParam Integer id) {
        return this.coursesService.delete(id);
    }

}
