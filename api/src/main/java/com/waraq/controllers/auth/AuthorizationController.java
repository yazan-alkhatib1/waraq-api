package com.waraq.controllers.auth;

import com.waraq.dto.auth.request.LoginRequest;
import com.waraq.dto.auth.response.LoginResponse;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.services.auth.AuthenticationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@Monitored
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    private final MessageSource messageSource;

    public AuthorizationController(AuthenticationService authenticationService, MessageSource messageSource) {
        this.authenticationService = authenticationService;
        this.messageSource = messageSource;
    }


    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginRequest request) {

        Response<LoginResponse> response = Response.<LoginResponse>builder()
                .data(authenticationService.login(request))
                .code(CODE.OK.getId())
                .message(messageSource.getMessage("message.ok", null, LocaleContextHolder.getLocale()))
                .success(true)
                .build();

        return new ResponseEntity<>(response, OK);
    }
}
