package com.waraq.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    private String url;

    private String method;

    private String className;

    @Getter(AccessLevel.NONE)
    private LocalTime startedAt;

    @Getter(AccessLevel.NONE)
    private LocalTime finishedAt;

    private Long duration;

    private String error;

    private String request;

    private String response;

    public void start() {
        this.startedAt = LocalTime.now();
    }

    public void end() {
        this.finishedAt = LocalTime.now();
        this.duration = Duration.between(startedAt, finishedAt).toMillis();
    }
}
