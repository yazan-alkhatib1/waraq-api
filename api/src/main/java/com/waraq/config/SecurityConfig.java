package com.waraq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waraq.filters.JwtFilter;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private static final String LOGIN_PATH = "/api/v1/auth/login";
    private static final String SIGNUP_PATH = "/api/v1/users/signup";

    private static final String API_KEY_USER_ID_PATH = "/api/v1/apikeys/user/**";

    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH");

    private static final List<String> ALLOWED_HEADERS = Arrays.asList("x-requested-with", "authorization", "Content-Type", "Authorization", "credential", "X-XSRF-TOKEN", "X-Refresh-Token", "X-Client-Id", "x-client-id", "x-api-key","Referer","User-Agent");

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(HttpMethod.POST, SIGNUP_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
                        .requestMatchers(HttpMethod.GET, API_KEY_USER_ID_PATH).permitAll()
                        .anyRequest().hasAuthority("user"))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, authException) -> accessDenied(response)));
        return httpSecurity.build();
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    private void accessDenied(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(Response.builder()
                .code(CODE.UNAUTHORIZED.getId())
                .message("Access denied")
                .success(false)
                .build()));
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
