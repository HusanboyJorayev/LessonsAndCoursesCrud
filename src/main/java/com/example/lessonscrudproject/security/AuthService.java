package com.example.lessonscrudproject.security;


import com.example.lessonscrudproject.dto.AuthDto;
import com.example.lessonscrudproject.dto.LoginDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.model.Auth;
import com.example.lessonscrudproject.repository.AuthRepository;
import com.example.lessonscrudproject.security.mapper.AuthMapper;
import com.example.lessonscrudproject.securityFilter.JwtUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthMapper authMapper;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public ResponseDto<AuthDto> create(AuthDto dto) {
        try {
            Auth auth = this.authMapper.toEntity(dto);
            auth.setCreatedAt(LocalDateTime.now());
            this.authRepository.save(auth);
            return ResponseDto.<AuthDto>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.authMapper.toDto(auth))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<AuthDto>builder()
                    .code(-1)
                    .message("Auth while saving error")
                    .build();
        }
    }

    public ResponseDto<AuthDto> get(Integer id) {

        return this.authRepository.findByAuthIdAndDeletedAtIsNull(id)
                .map(auth -> ResponseDto.<AuthDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(this.authMapper.toDtoWithAuthorities(auth))
                        .build())
                .orElse(ResponseDto.<AuthDto>builder()
                        .code(-1)
                        .message("Auth is not found")
                        .build());

    }
    @Override
    public AuthDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByUsernameAndEnabledIsTrueAndDeletedAtIsNull(username)
                .map(this.authMapper::toDtoWithAuthorities)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("this %s :: username is not found", username)));
    }

    Gson gson=new Gson();
    public ResponseDto<String> singIn(LoginDto loginDto) {
        AuthDto userDto = this.loadUserByUsername(loginDto.getUsername());
        if (userDto == null) {
            throw new UsernameNotFoundException(String.format("User with username :: %s is not found!", loginDto.getUsername()));
        }
        if (this.passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {

            String json = gson.toJson(userDto);

            return ResponseDto.<String>builder()
                    .success(true)
                    .message("OK")
                    .data(jwtUtil.generateToken(json))
                    .build();
        }
        throw new BadCredentialsException("Password is uncorrected!");
    }
}
