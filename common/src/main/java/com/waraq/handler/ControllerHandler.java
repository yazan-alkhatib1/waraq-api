package com.waraq.handler;


import com.waraq.exceptions.AuthenticationException;
import com.waraq.exceptions.BadRequestException;
import com.waraq.exceptions.InvalidApiKeyException;
import com.waraq.exceptions.InvalidJwtTokenException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.exceptions.PaymentGatewaysException;
import com.waraq.exceptions.WaraqException;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ControllerAdvice
@Slf4j
public class ControllerHandler {

    private final MessageSource messageSource;

    public ControllerHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleBadRequestException(MethodArgumentNotValidException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(Arrays.stream(Objects.requireNonNull(ex.getDetailMessageArguments())).map(Object::toString).collect(Collectors.joining(",")))
                .code(CODE.BAD_REQUEST.getId())
                .message(messageSource.getMessage("message.bad.request", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<String>> handleBadRequestException(BadRequestException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.BAD_REQUEST.getId())
                .message(messageSource.getMessage("message.bad.request", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<String>> handleNotFoundException(NotFoundException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.NOT_FOUND.getId())
                .message(messageSource.getMessage("message.not.found", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response<String>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.UNAUTHORIZED.getId())
                .message(messageSource.getMessage("message.unauthorized", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<Response<String>> handleInvalidApiKeyException(InvalidApiKeyException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.UNAUTHORIZED.getId())
                .message(messageSource.getMessage("message.unauthorized", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Response<String>> handleInvalidJwtException(InvalidJwtTokenException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.UNAUTHORIZED.getId())
                .message(messageSource.getMessage("message.unauthorized", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Response<String>> handlePSQLException(PSQLException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.INTERNAL_SERVER_ERROR.getId())
                .message(messageSource.getMessage("message.internal.server.error", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<String>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.BAD_REQUEST.getId())
                .message(messageSource.getMessage("message.bad.request", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentGatewaysException.class)
    public ResponseEntity<Response<String>> handlePaymentGatewayException(PaymentGatewaysException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(nonNull(ex.getErrors()) ? String.join(",", ex.getErrors()) : null)
                .code(CODE.UNPROCESSABLE_ENTITY.getId())
                .message(nonNull(ex.getDescription()) ? ex.getDescription() : "Error happened while processing payment gateway request")
                .paymentGatewayCode(nonNull(ex.getCode()) ? ex.getCode() : null)
                .success(false)
                .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(WaraqException.class)
    public ResponseEntity<Response<String>> handleVaultException(WaraqException ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.INTERNAL_SERVER_ERROR.getId())
                .message(messageSource.getMessage("message.internal.server.error", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleException(Exception ex) {
        log.error("Error during request processing ", ex);
        return new ResponseEntity<>(Response.<String>builder()
                .data(ex.getMessage())
                .code(CODE.INTERNAL_SERVER_ERROR.getId())
                .message(messageSource.getMessage("message.internal.server.error", null, LocaleContextHolder.getLocale()))
                .success(false)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
