package com.example.lessonscrudproject.controller;


import com.example.lessonscrudproject.dto.AuthoritiesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.security.AuthoritiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "authorities")
public class AuthoritiesController /*implements SimpleCrud<Integer, AuthoritiesDto>*/ {

    private final AuthoritiesService authoritiesService;


    @PostMapping(value = "/")
    @Operation(
            description = "this  method creates authorities",
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
    public ResponseDto<AuthoritiesDto> create(@RequestBody AuthoritiesDto dto) {
        return this.authoritiesService.create(dto);
    }


    @GetMapping(value = "/{u}")
    @Operation(
            description = "this  method gets authorities",
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
    public ResponseDto<AuthoritiesDto> get(@PathVariable(value = "u") String username) {
        return this.authoritiesService.get(username);
    }


    @PutMapping(value = "/{u}")
    @Operation(
            description = "this  method updates authorities",
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
    public ResponseDto<AuthoritiesDto> update(@RequestBody AuthoritiesDto dto, @PathVariable(value = "u") String username) {
        return this.authoritiesService.update(dto, username);
    }


    @DeleteMapping(value = "/{u}")
    @Operation(
            description = "this  method deletes authorities",
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
    public ResponseDto<AuthoritiesDto> delete(@PathVariable(value = "u") String username) {
        return this.authoritiesService.delete(username);
    }


    @GetMapping(value = "/getAll")
    @Operation(
            description = "this  method gets all authorities",
            method = "getAll",
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
            summary = "this is a getAll method"
    )
    public ResponseDto<List<AuthoritiesDto>> getAll() {
        return this.authoritiesService.getAll();
    }
}
