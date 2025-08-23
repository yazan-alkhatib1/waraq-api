package com.waraq.helpers;

import com.waraq.dto.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserLoader {

    public static CustomUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static Long systemId() {
        return -1L;
    }
}
