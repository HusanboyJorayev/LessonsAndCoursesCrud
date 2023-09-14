package com.example.lessonscrudproject.config;



import com.example.lessonscrudproject.securityFilter.JwtSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecConfig {


    private final JwtSecurityFilter filter;
    //private final SecurityFilter filter;
    /*private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;*/

    /* @Autowired // todo:inMemoryAuthentication
     public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
         builder.inMemoryAuthentication()
                 .withUser("User")
                 .password(passwordEncoder.encode("root"))
                 .roles("Admin").and()
                 .withUser("Card")
                 .password(passwordEncoder.encode("1234"))
                 .roles("User").and()
                 .passwordEncoder(passwordEncoder);
     }*/
   /* @Autowired // todo:jdbcAuthentication
    public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }*/

    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**", "/card/**", "/auth/**", "/authorities/**").permitAll()
                .anyRequest()
                .authenticated().and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
