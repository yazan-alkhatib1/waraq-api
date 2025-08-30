package com.waraq.service.services.auth;

import com.waraq.dto.auth.CustomUserDetails;
import com.waraq.dto.auth.request.LoginRequest;
import com.waraq.dto.auth.response.LoginResponse;
import com.waraq.exceptions.AuthenticationException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.service.helpers.JwtTokenManager;
import com.waraq.service.security.CustomUserDetailsService;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.waraq.validator.CompositeValidator.isStringEmpty;
import static com.waraq.validator.CompositeValidator.joinViolations;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenManager jwtTokenManager;

    private final MessageSource messageSource;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     CustomUserDetailsService customUserDetailsService,
                                     JwtTokenManager jwtTokenManager, MessageSource messageSource) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenManager = jwtTokenManager;
        this.messageSource = messageSource;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login request for email {}", request.getEmail());

        validateLoginRequest(request);

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        authenticate(request.getEmail().toLowerCase(), request.getPassword());

        return LoginResponse.builder()
                .id(customUserDetails.getId())
                .isEnabled(customUserDetails.getIsEnabled())
                .firstName(customUserDetails.getFirstName())
                .lastName(customUserDetails.getLastName())
                .email(customUserDetails.getEmail())
                .phoneNumber(customUserDetails.getPhoneNumber())
                .jwtToken(jwtTokenManager.generateAccessToken(customUserDetails))
                .build();
    }

    private void validateLoginRequest(LoginRequest request) {
        List<String> violations = new CompositeValidator<LoginRequest, String>()
                .addValidator(p -> !isStringEmpty(p.getEmail()), messageSource.getMessage("error.message.email.not.empty", null, LocaleContextHolder.getLocale()))
                .addValidator(p -> !isStringEmpty(p.getPassword()), messageSource.getMessage("error.message.password.not.empty", null, LocaleContextHolder.getLocale()))
                .validate(request);
        joinViolations(violations);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException(messageSource.getMessage("error.message.merchant.disabled", null, LocaleContextHolder.getLocale()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(messageSource.getMessage("error.message.incorrect.password", null, LocaleContextHolder.getLocale()));
        } catch (AuthenticationServiceException e) {
            throw new NotFoundException(messageSource.getMessage("error.message.email.not.found", null, LocaleContextHolder.getLocale()));
        }
    }
}
