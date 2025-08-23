package com.waraq.service.helpers;

import com.waraq.helpers.UserLoader;

import java.util.UUID;

public class UniqueUserIdGenerator {

    public static String generate(String userId) {

        String concatenatedString = userId + UserLoader.userDetails().getId();

        return UUID.nameUUIDFromBytes(concatenatedString.getBytes()).toString();
    }
}
