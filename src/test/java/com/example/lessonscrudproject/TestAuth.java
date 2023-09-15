package com.example.lessonscrudproject;

import com.example.lessonscrudproject.dto.*;
import com.example.lessonscrudproject.model.Auth;
import com.example.lessonscrudproject.repository.AuthAccessSessionRepository;
import com.example.lessonscrudproject.repository.AuthRefreshSessionRepository;
import com.example.lessonscrudproject.repository.AuthRepository;
import com.example.lessonscrudproject.repository.AuthoritiesRepository;
import com.example.lessonscrudproject.security.AuthService;
import com.example.lessonscrudproject.security.mapper.AuthMapper;
import com.example.lessonscrudproject.security.validation.AuthValidation;
import com.example.lessonscrudproject.securityFilter.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestAuth {

    private AuthAccessSessionRepository authAccessSessionRepository;
    private AuthRefreshSessionRepository authRefreshSessionRepository;
    private AuthoritiesRepository authoritiesRepository;
    private RegisterConfirmDto registerConfirmDto;
    private AuthValidation authValidation;
    private AuthRepository authRepository;
    private AuthMapper authMapper;
    private AuthService authService;
    private JwtUtils jwtUtils;

    @BeforeEach
    void initObject(){
        this.authAccessSessionRepository=mock(AuthAccessSessionRepository.class);
        this.authRefreshSessionRepository=mock(AuthRefreshSessionRepository.class);
        this.authoritiesRepository=mock(AuthoritiesRepository.class);
        this.registerConfirmDto=mock(RegisterConfirmDto.class);
        this.authValidation=mock(AuthValidation.class);
        this.authRepository=mock(AuthRepository.class);
        this.authMapper=mock(AuthMapper.class);
        this.jwtUtils=mock(JwtUtils.class);
        this.authService=new AuthService(jwtUtils,authMapper,authRepository,
                authValidation,authoritiesRepository,authAccessSessionRepository,
                authRefreshSessionRepository);
    }
    @Test
    void registerValidation(){
        when(this.authValidation.validate(any()))
                .thenReturn(List.of(new ErrorDto("Validation error","validation")));

        ResponseDto<AuthDto>response=this.authService.register(any());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(),-3);
        Assertions.assertFalse(response.isSuccess());

        verify(this.authValidation,times(1)).validate(any());
    }
    @Test
    void registerPositive(){
        AuthDto authDto= AuthDto.builder()
                .authId(1)
                .name("Husanboy")
                .username("Jorayev")
                .build();

        Auth auth= Auth.builder()
                .authId(1)
                .name("Husanboy")
                .username("Jorayev")
                .build();

        when(this.authMapper.toDto(any()))
                .thenReturn(authDto);
        when(this.authMapper.toEntity(any()))
                .thenReturn(auth);

        ResponseDto<AuthDto>response=this.authService.register(any());
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(response.getCode(),0);

        verify(this.authMapper,times(1)).toDto(any());
        verify(this.authMapper,times(1)).toEntity(any());
        verify(this.authRepository,times(1)).save(any());
    }
    @Test
    void registerException(){
        when(this.authMapper.toEntity(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<AuthDto>response=this.authService.register(any());
        Assertions.assertEquals(response.getCode(),-1);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        verify(this.authMapper,times(1)).toEntity(any());
    }
    @Test
    void registerConfirmPositive(){
        TokenResponseDto tokenResponseDto= TokenResponseDto.builder()
                .accessToken("qwertasdf")
                .refreshToken("dfghj")
                .build();

        RegisterConfirmDto dto= RegisterConfirmDto.builder()
                .username("941490908")
                .code("1234")
                .build();

        Auth auth=Auth.builder()
                .username("941490908")
                .password("1234")
                .enabled(true)
                .build();

        when(this.authRepository.save(any()))
                .thenReturn(auth);



        when(this.authRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername()))
                .thenReturn(Optional.ofNullable(auth));

        ResponseDto<TokenResponseDto>response=this.authService.registerConfirm(any());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(),0);
        Assertions.assertTrue(response.isSuccess());

        verify(this.authMapper,times(1)).toDto(any());
        verify(this.jwtUtils,times(1)).generateToken(any());
    }
}
