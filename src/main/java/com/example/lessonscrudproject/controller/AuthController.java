package com.example.lessonscrudproject.controller;



import com.example.lessonscrudproject.dto.*;
import com.example.lessonscrudproject.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseDto<AuthDto> register(@RequestBody @Valid AuthDto authDto) {
        return this.authService.register(authDto);
    }

    @PostMapping(value = "/register_confirm")
    public ResponseDto<TokenResponseDto> registerConfirm(@RequestBody @Valid RegisterConfirmDto dto) {
        return this.authService.registerConfirm(dto);
    }

    @PostMapping(value = "/login")
    public ResponseDto<TokenResponseDto> login(@RequestBody@Valid LoginDto dto) {
        return this.authService.login(dto);
    }

    @PostMapping(value = "/logOut")
    public ResponseDto<AuthDto> logOut(@RequestBody @Valid LoginDto dto) {
        return this.authService.logOut(dto);
    }

    @GetMapping(value = "/refresh-token")
    public ResponseDto<TokenResponseDto> refreshToken(@RequestParam String token) {
        return this.authService.refreshToken(token);
    }

   /* @GetMapping(value = "/get")
    public ResponseDto<AuthDto> get(@RequestParam Integer id) {
        return this.authService.get(id);
    }*/

}
