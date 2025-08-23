package com.waraq.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waraq.exceptions.InvalidJwtTokenException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.service.helpers.JwtTokenManager;
import com.waraq.service.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer ";

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            if (nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
                String token = authorizationHeader.substring(7);
                if (!jwtTokenManager.isTokenValid(token))
                    throw new InvalidJwtTokenException("Invalid Token");
                String username = jwtTokenManager.getEmailFromToken(token);
                if (username == null) {
                    throw new NotFoundException("user not found");
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String language = nonNull(request.getHeader(ACCEPT_LANGUAGE)) ? request.getHeader(ACCEPT_LANGUAGE) : Locale.ENGLISH.getLanguage();
                Locale locale = Locale.forLanguageTag(language);
                LocaleContextHolder.setLocale(locale);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) { //TODO Enhance
            log.error("Error during request : ", e);

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(Response.builder()
                    .code(CODE.UNAUTHORIZED.getId())
                    .message(messageSource.getMessage("message.access.denied", null, LocaleContextHolder.getLocale()))
                    .success(false)
                    .build()));
        }
    }
}
