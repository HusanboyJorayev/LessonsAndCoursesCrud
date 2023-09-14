package com.example.lessonscrudproject.controller;


import com.example.lessonscrudproject.dto.*;
import com.example.lessonscrudproject.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    @Operation(
            description = "this  method registers user",
            method = "Register",
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
            summary = "this is a register method"
    )
    public ResponseDto<AuthDto> register(@RequestBody @Valid AuthDto authDto) {
        return this.authService.register(authDto);
    }

    @PostMapping(value = "/register_confirm")
    @Operation(
            description = "this  method register_confirm user",
            method = "register_confirm",
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
            summary = "this is a register_confirm method"
    )
    public ResponseDto<TokenResponseDto> registerConfirm(@RequestBody @Valid RegisterConfirmDto dto) {
        return this.authService.registerConfirm(dto);
    }

    @PostMapping(value = "/login")
    @Operation(
            description = "this  method logins user",
            method = "login",
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
            summary = "this is a login method"
    )
    public ResponseDto<TokenResponseDto> login(@RequestBody @Valid LoginDto dto) {
        return this.authService.login(dto);
    }

    @PostMapping(value = "/logOut")
    @Operation(
            description = "this  method logOut user",
            method = "logout",
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
            summary = "this is a logout method"
    )
    public ResponseDto<AuthDto> logOut(@RequestBody @Valid LoginDto dto) {
        return this.authService.logOut(dto);
    }

    @GetMapping(value = "/refresh-token")
    @Operation(
            description = "this  method refresh-token user",
            method = "refresh-token",
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
            summary = "this is a refresh-token method"
    )
    public ResponseDto<TokenResponseDto> refreshToken(@RequestParam String token) {
        return this.authService.refreshToken(token);
    }

   /* @GetMapping(value = "/get")
    public ResponseDto<AuthDto> get(@RequestParam Integer id) {
        return this.authService.get(id);
    }*/

}
