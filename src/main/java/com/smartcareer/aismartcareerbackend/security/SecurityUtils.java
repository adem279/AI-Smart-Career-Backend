package com.smartcareer.aismartcareerbackend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("Aucun utilisateur authentifié");
        }
        return (CustomUserDetails) auth.getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUserDetails().getUsername();
    }

    public static String getCurrentUserType() {
        return getCurrentUserDetails().getUserType();
    }
}
