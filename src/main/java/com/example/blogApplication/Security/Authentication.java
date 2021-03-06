package com.example.blogApplication.Security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Authentication implements UserAuthentication {

    @Override
    public org.springframework.security.core.Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
