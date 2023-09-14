package com.example.lessonscrudproject;

import com.example.lessonscrudproject.dto.AuthoritiesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.model.Authorities;
import com.example.lessonscrudproject.repository.AuthoritiesRepository;
import com.example.lessonscrudproject.security.AuthoritiesService;
import com.example.lessonscrudproject.security.mapper.AuthoritiesMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestAuthorities {
    private AuthoritiesService authoritiesService;
    private AuthoritiesMapper authoritiesMapper;
    private AuthoritiesRepository authoritiesRepository;

    @BeforeEach
    void initObject() {
        this.authoritiesMapper = mock(AuthoritiesMapper.class);
        this.authoritiesRepository = mock(AuthoritiesRepository.class);
        this.authoritiesService = new AuthoritiesService(authoritiesRepository, authoritiesMapper);
    }

    @Test
    void createPositive() {
        AuthoritiesDto authoritiesDto = AuthoritiesDto.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        Authorities authorities = Authorities.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        when(this.authoritiesMapper.toDto(any()))
                .thenReturn(authoritiesDto);
        when(this.authoritiesMapper.toEntity(any()))
                .thenReturn(authorities);

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.create(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);

        verify(this.authoritiesMapper, times(1)).toDto(any());
        verify(this.authoritiesMapper, times(1)).toEntity(any());
        verify(this.authoritiesRepository, times(1)).save(any());
    }

    @Test
    void createException() {
        when(this.authoritiesMapper.toEntity(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.create(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.authoritiesMapper, times(1)).toEntity(any());
    }

    @Test
    void getPositive() {
        AuthoritiesDto authoritiesDto = AuthoritiesDto.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        Authorities authorities = Authorities.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        when(this.authoritiesMapper.toDto(any()))
                .thenReturn(authoritiesDto);
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(authorities));

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.get(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getCode(), 0);

        verify(this.authoritiesMapper, times(1)).toDto(any());
        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
    }

    @Test
    void getNegative() {
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.get(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
    }

    @Test
    void updatePositive() {
        AuthoritiesDto authoritiesDto = AuthoritiesDto.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        Authorities authorities = Authorities.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        when(this.authoritiesMapper.toDto(any()))
                .thenReturn(authoritiesDto);
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(authorities));

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.update(authoritiesDto, any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertNotNull(response.getData());

        verify(this.authoritiesMapper, times(1)).toDto(any());
        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
        verify(this.authoritiesRepository, times(1)).save(any());
    }

    @Test
    void updateNegative() {
        AuthoritiesDto authoritiesDto = new AuthoritiesDto();
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());
        ResponseDto<AuthoritiesDto> response = this.authoritiesService.update(authoritiesDto, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
    }

    @Test
    void updateException() {
        AuthoritiesDto authoritiesDto = new AuthoritiesDto();
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.update(authoritiesDto, any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(response.getCode(), -1);
        Assertions.assertNull(response.getData());

        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
    }

    @Test
    void deletePositive() {
        AuthoritiesDto authoritiesDto = AuthoritiesDto.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        Authorities authorities = Authorities.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.ofNullable(authorities));
        when(this.authoritiesMapper.toDto(any()))
                .thenReturn(authoritiesDto);

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.delete(any());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertNotNull(response.getData());

        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
        verify(this.authoritiesMapper, times(1)).toDto(any());
        verify(this.authoritiesRepository, times(1)).save(any());
    }

    @Test
    void deleteNegative() {
        when(this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<AuthoritiesDto> response = this.authoritiesService.delete(any());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.authoritiesRepository, times(1)).findByUsernameAndDeletedAtIsNull(any());
    }

    @Test
    void getAllPositive() {
        AuthoritiesDto authoritiesDto = AuthoritiesDto.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        Authorities authorities = Authorities.builder()
                .authority("Admin")
                .authId(1)
                .username("Husanboy")
                .build();

        when(this.authoritiesMapper.toDto(any()))
                .thenReturn(authoritiesDto);

        when(this.authoritiesRepository.findAllWithQuery())
                .thenReturn(List.of(authorities));

        ResponseDto<List<AuthoritiesDto>> response = this.authoritiesService.getAll();
        Assertions.assertEquals(response.getCode(), 0);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());

        verify(this.authoritiesMapper, times(1)).toDto(any());
        verify(this.authoritiesRepository, times(1)).findAllWithQuery();
    }

    @Test
    void getAllNegative() {
        when(this.authoritiesRepository.findAllWithQuery())
                .thenReturn(List.of());
        ResponseDto<List<AuthoritiesDto>> response = this.authoritiesService.getAll();
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(response.getCode(), -1);

        verify(this.authoritiesRepository, times(1)).findAllWithQuery();
    }
}
