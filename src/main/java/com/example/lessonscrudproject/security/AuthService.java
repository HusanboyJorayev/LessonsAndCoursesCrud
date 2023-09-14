package com.example.lessonscrudproject.security;



import com.example.lessonscrudproject.dto.*;
import com.example.lessonscrudproject.model.Auth;
import com.example.lessonscrudproject.model.AuthAccessSession;
import com.example.lessonscrudproject.model.AuthRefreshSession;
import com.example.lessonscrudproject.repository.AuthAccessSessionRepository;
import com.example.lessonscrudproject.repository.AuthRefreshSessionRepository;
import com.example.lessonscrudproject.repository.AuthRepository;
import com.example.lessonscrudproject.repository.AuthoritiesRepository;
import com.example.lessonscrudproject.security.mapper.AuthMapper;
import com.example.lessonscrudproject.security.validation.AuthValidation;
import com.example.lessonscrudproject.securityFilter.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;
    private final AuthRepository authRepository;
    private final AuthValidation authValidation;
    private final AuthoritiesRepository authoritiesRepository;
    private final AuthAccessSessionRepository authAccessSessionRepository;
    private final AuthRefreshSessionRepository authRefreshSessionRepository;

    public ResponseDto<AuthDto> register(AuthDto dto) {
        List<ErrorDto>errors=this.authValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<AuthDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .errors(errors)
                    .build();
        }
        try {
            Auth auth = this.authMapper.toEntity(dto);
            auth.setCreatedAt(LocalDateTime.now());

          /*  this.authoritiesRepository.save(Authorities.builder()
                    .authId(auth.getAuthId())
                    .authority("USer")
                    .username(auth.getUsername())
                    .build());*/

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


    @Transactional
    public ResponseDto<TokenResponseDto> registerConfirm(RegisterConfirmDto dto) {
        return this.authRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername())
                .map(auth -> {
                    auth.setEnabled(true);
                    Auth save = this.authRepository.save(auth);
                    String token = toJsonByAuth(save);
                    String jwtToken = this.jwtUtils.generateToken(token);
                    var simpleAuthDto = this.authMapper.toDto(save);

                    checkValidToken(token);

                    this.authAccessSessionRepository.save(new AuthAccessSession(
                            token, simpleAuthDto
                    ));

                    this.authRefreshSessionRepository.save(new AuthRefreshSession(
                            token, simpleAuthDto
                    ));

                    return ResponseDto.<TokenResponseDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(TokenResponseDto.builder()
                                    .accessToken(jwtToken)
                                    .refreshToken(jwtToken)
                                    .build())
                            .build();
                })
                .orElse(ResponseDto.<TokenResponseDto>builder()
                        .code(-1)
                        .message(String.format("Auth with %S:username is not found", dto.getUsername()))
                        .build());


    }
    @Transactional
    public ResponseDto<TokenResponseDto> login(LoginDto dto) {
        return this.authRepository.findByUsernameAndEnabledIsTrueAndDeletedAtIsNull(dto.getUsername())
                .map(auth -> {
                    var simpleAuthDto = this.authMapper.toDto(auth);
                    String token = toJsonByAuth(auth);

                    checkValidToken(token);

                    String jwtToken = this.jwtUtils.generateToken(token);

                    this.authAccessSessionRepository.save(new AuthAccessSession(
                            token, simpleAuthDto
                    ));

                    this.authRefreshSessionRepository.save(new AuthRefreshSession(
                            token, simpleAuthDto
                    ));
                    return ResponseDto.<TokenResponseDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(TokenResponseDto.builder()
                                    .accessToken(jwtToken)
                                    .refreshToken(jwtToken)
                                    .build())
                            .build();
                })
                .orElse(ResponseDto.<TokenResponseDto>builder()
                        .code(-1)
                        .message(String.format("Auth with %S:username is not valid", dto.getUsername()))
                        .build());

    }
    public ResponseDto<TokenResponseDto> refreshToken(String token) {
        if (!jwtUtils.isValid(token)) return ResponseDto.<TokenResponseDto>builder()
                .code(-3)
                .message("Token is not valid")
                .build();
        return this.authRefreshSessionRepository.findById(token)
                .map(authRefreshSession -> {
                    checkValidToken(token);
                    AuthDto authDto = authRefreshSession.getAuthDto();
                    Auth auth = this.authMapper.toEntity(authDto);
                    auth.setEnabled(true);
                    String newSubject = toJsonByAuth(auth);

                    this.authAccessSessionRepository.save(new AuthAccessSession(
                            newSubject, this.authMapper.toDto(auth)
                    ));

                    this.authRefreshSessionRepository.save(new AuthRefreshSession(
                            newSubject, this.authMapper.toDto(auth)
                    ));

                    String newToken = this.jwtUtils.generateToken(newSubject);
                    return ResponseDto.<TokenResponseDto>builder()
                            .success(true)
                            .message("OK")
                            .data(TokenResponseDto.builder()
                                    .accessToken(newToken)
                                    .refreshToken(newToken)
                                    .build())
                            .build();
                })
                .orElse(ResponseDto.<TokenResponseDto>builder()
                        .code(-1)
                        .message("Auth is not found")
                        .build());
    }

    public ResponseDto<AuthDto> logOut(LoginDto dto) {
        return this.authRepository.findByUsernameAndEnabledIsTrueAndDeletedAtIsNull(dto.getUsername())
                .map(auth -> {
                    auth.setEnabled(false);
                    this.authRepository.save(auth);
                    return ResponseDto.<AuthDto>builder()
                            .success(true)
                            .message("Ok")
                            .build();
                })
                .orElse(ResponseDto.<AuthDto>builder()
                        .code(-1)
                        .message("this username is logOut")
                        .build());

    }

    /*  public ResponseDto<AuthDto> get(Integer id) {

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

      }*/
    @Override
    public AuthDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByUsernameAndEnabledIsTrueAndDeletedAtIsNull(username)
                .map(this.authMapper::toDtoWithAuthorities)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("this %s :: username is not found", username)));
    }


    private void checkValidToken(String token) {

        this.authAccessSessionRepository.findById(token)
                .ifPresent(this.authAccessSessionRepository::delete);

        this.authRefreshSessionRepository.findById(token)
                .ifPresent(this.authRefreshSessionRepository::delete);
    }

    private String toJsonByAuth(Auth auth) {
        return
                "authId-" + auth.getAuthId() +
                        ", name-'" + auth.getName() + '\'' +
                        ", surname-'" + auth.getSurname() + '\'' +
                        ", username-'" + auth.getUsername() + '\'' +
                        "enabled-" + auth.getEnabled();
    }
}
