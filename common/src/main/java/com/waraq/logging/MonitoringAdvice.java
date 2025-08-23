package com.waraq.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class MonitoringAdvice {

    private final ObjectMapper objectMapper;
    private final DataFeed dataFeed;
    private final HttpServletRequest httpServletRequest;


    public MonitoringAdvice(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.dataFeed = new MonitoringDataFeed();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Around("within(@com.waraq.logging.Monitored *) || @annotation(com.waraq.logging.Monitored)")
    public Object logEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        Event event = new Event();
        try {
            event.start();
            event.setUrl(httpServletRequest.getRequestURL().toString());
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            event.setClassName(method.getDeclaringClass().getSimpleName());
            event.setMethod(method.getName());
            event.setRequest(objectMapper.writeValueAsString(joinPoint.getArgs()));
        } catch (Exception e2) {
            log.error(MarkerFactory.getMarker("ERROR"), "error in monitoring", e2);
            event.setError(e2.getMessage());
        }
        Object proceed;
        try {
            proceed = joinPoint.proceed();
            event.setResponse(objectMapper.writeValueAsString(proceed));
        } catch (Throwable throwable) {
            log.error(MarkerFactory.getMarker("ERROR"), "error in monitoring", throwable);
            event.setError(throwable.getMessage());
            throw throwable;
        } finally {
            event.end();
            dataFeed.log(event);
        }
        return proceed;
    }
}
