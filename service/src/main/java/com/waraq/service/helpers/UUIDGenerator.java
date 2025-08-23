package com.waraq.service.helpers;

import java.time.LocalDateTime;
import java.util.UUID;

public class UUIDGenerator {

    public static String generateUUID(Long merchantId) {

        String concatenatedString = LocalDateTime.now() + ":" + merchantId;

        return UUID.nameUUIDFromBytes(concatenatedString.getBytes()).toString();
    }
}