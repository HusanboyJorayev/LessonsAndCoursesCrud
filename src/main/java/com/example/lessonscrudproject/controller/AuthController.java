package com.example.lessonscrudproject.controller;


import com.example.lessonscrudproject.dto.AuthDto;
import com.example.lessonscrudproject.dto.LoginDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/")
    public ResponseDto<AuthDto> create(@RequestBody AuthDto authDto) {
        return this.authService.create(authDto);
    }

    @GetMapping(value = "/get")
    public ResponseDto<AuthDto> get(@RequestParam Integer id) {
        return this.authService.get(id);
    }

    @PostMapping("/sign-in")
    public ResponseDto<String> singIn(@RequestBody LoginDto loginDto){
        return authService.singIn(loginDto);
    }
}
