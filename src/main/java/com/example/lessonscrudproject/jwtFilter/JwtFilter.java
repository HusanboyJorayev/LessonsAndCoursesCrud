package com.example.lessonscrudproject.jwtFilter;

import com.example.lessonscrudproject.dto.AuthDto;
import com.example.lessonscrudproject.securityFilter.JwtUtil;
import com.google.gson.Gson;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter {

    private final Gson gson;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (!StringUtils.isBlank(authorization) && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            if (jwtUtil.isValid(token)) {

                String sub = jwtUtil.getClaim("sub", token, String.class);
                AuthDto authDto = gson.fromJson(sub, AuthDto.class);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                authDto,
                                null,
                                authDto.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
