package com.waraq.http_response;

public enum CODE {
    OK(200),
    CREATED(201),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    UNPROCESSABLE_ENTITY(422);

    final int id;

    CODE(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
