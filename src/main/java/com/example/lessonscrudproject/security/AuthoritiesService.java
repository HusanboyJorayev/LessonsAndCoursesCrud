package com.example.lessonscrudproject.security;


import com.example.lessonscrudproject.dto.AuthoritiesDto;
import com.example.lessonscrudproject.dto.ResponseDto;
import com.example.lessonscrudproject.model.Authorities;
import com.example.lessonscrudproject.repository.AuthoritiesRepository;
import com.example.lessonscrudproject.security.mapper.AuthoritiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthoritiesService /*implements SimpleCrud<Integer, AuthoritiesDto>*/ {

    private final AuthoritiesRepository authoritiesRepository;
    private final AuthoritiesMapper authoritiesMapper;


    public ResponseDto<AuthoritiesDto> create(AuthoritiesDto dto) {
        try {
            Authorities authority = this.authoritiesMapper.toEntity(dto);
            authority.setCreatedAt(LocalDateTime.now());
            this.authoritiesRepository.save(authority);
            return ResponseDto.<AuthoritiesDto>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.authoritiesMapper.toDto(authority))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .code(-1)
                    .message("while authority saving error")
                    .build();
        }
    }

    public ResponseDto<AuthoritiesDto> get(String username) {

        return this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(username)
                .map(authority -> ResponseDto.<AuthoritiesDto>builder()
                        .success(true)
                        .message("Ok")
                        .data(this.authoritiesMapper.toDto(authority))
                        .build())
                .orElse(ResponseDto.<AuthoritiesDto>builder()
                        .code(-1)
                        .message("authority is not found")
                        .build());
    }


    public ResponseDto<AuthoritiesDto> update(AuthoritiesDto dto, String username) {
        try {

            return this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(username)
                    .map(authority -> {
                        authority.setUpdatedAt(LocalDateTime.now());
                        this.authoritiesMapper.update(dto, authority);
                        this.authoritiesRepository.save(authority);
                        return ResponseDto.<AuthoritiesDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.authoritiesMapper.toDto(authority))
                                .build();
                    })
                    .orElse(ResponseDto.<AuthoritiesDto>builder()
                            .code(-1)
                            .message("authority is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .code(-1)
                    .message("while authority updating error")
                    .build();
        }
    }


    public ResponseDto<AuthoritiesDto> delete(String username) {

        return this.authoritiesRepository.findByUsernameAndDeletedAtIsNull(username)
                .map(authority -> {
                    authority.setDeletedAt(LocalDateTime.now());
                    this.authoritiesRepository.save(authority);
                    return ResponseDto.<AuthoritiesDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.authoritiesMapper.toDto(authority))
                            .build();
                })
                .orElse(ResponseDto.<AuthoritiesDto>builder()
                        .code(-1)
                        .message("authority is not found")
                        .build());

    }

    public ResponseDto<List<AuthoritiesDto>> getAll() {
        List<Authorities> list = this.authoritiesRepository.findAllWithQuery();
        if (list.isEmpty()) {
            return ResponseDto.<List<AuthoritiesDto>>builder()
                    .code(-1)
                    .message("Authorities are not found")
                    .build();
        }
        return ResponseDto.<List<AuthoritiesDto>>builder()
                .success(true)
                .message("Ok")
                .data(list.stream().map(this.authoritiesMapper::toDto).toList())
                .build();
    }

    public ResponseDto<Page<AuthoritiesDto>> getAllPage(Integer page, Integer size) {
        Page<Authorities> authorities = this.authoritiesRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (authorities.isEmpty()) {
            return ResponseDto.<Page<AuthoritiesDto>>builder()
                    .code(-1)
                    .message("Authorities are not found")
                    .build();
        }
        return ResponseDto.<Page<AuthoritiesDto>>builder()
                .success(true)
                .message("Ok")
                .data(authorities.map(this.authoritiesMapper::toDto))
                .build();
    }
}
