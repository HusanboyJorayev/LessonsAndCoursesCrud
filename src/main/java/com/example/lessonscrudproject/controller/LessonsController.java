package com.example.lessonscrudproject.controller;

import com.example.lessonscrudproject.dto.CoursesDto;
import com.example.lessonscrudproject.dto.LessonsDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.dto.SimpleCrud;
import com.example.lessonscrudproject.security.LessonsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lesson")
public class LessonsController implements SimpleCrud<Integer, LessonsDto> {
    private final LessonsService lessonsService;

    @Override
    @PostMapping(value = "/create")
    @Operation(
            description = "this  method creates lessons",
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
    public ResponseDto<LessonsDto> create(@RequestBody @Valid LessonsDto dto) {
        return this.lessonsService.create(dto);
    }

    @Override
    @GetMapping(value = "/get")
    @Operation(
            description = "this  method gets lessons",
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
    public ResponseDto<LessonsDto> get(@RequestParam Integer id) {
        return this.lessonsService.get(id);
    }

    @Override
    @PutMapping(value = "/update{id}")
    @Operation(
            description = "this  method updates lessons",
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
    public ResponseDto<LessonsDto> update(@RequestBody @Valid LessonsDto dto, @PathVariable Integer id) {
        return this.lessonsService.update(dto, id);
    }

    @Override
    @DeleteMapping(value = "/delete")
    @Operation(
            description = "this  method deletes lessons",
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
    public ResponseDto<LessonsDto> delete(@RequestParam Integer id) {
        return this.lessonsService.delete(id);
    }
}
