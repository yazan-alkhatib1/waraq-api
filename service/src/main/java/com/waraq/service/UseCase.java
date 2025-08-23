package com.waraq.service;

public interface UseCase<REQ,RES> {
    RES execute(REQ request);
}
