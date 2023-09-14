package com.example.lessonscrudproject.securityFilter;



import com.example.lessonscrudproject.repository.AuthAccessSessionRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthAccessSessionRepository authAccessSessionRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (this.jwtUtils.isValid(token)) {
                String sessionId = this.jwtUtils.getClaims(token, "sub", String.class);
                this.authAccessSessionRepository.findById(sessionId)
                        .ifPresent(authAccessSession -> {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            authAccessSession.getAuthDto(),
                                            authAccessSession.getAuthDto().getPassword(),
                                            authAccessSession.getAuthDto().getAuthorities()
                                    );

                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        });
            }
        }
        filterChain.doFilter(request, response);
    }

}
