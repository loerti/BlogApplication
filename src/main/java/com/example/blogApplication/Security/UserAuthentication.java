package com.example.blogApplication.Security;

import org.springframework.security.core.Authentication;

public interface UserAuthentication {
    Authentication getAuthentication();
}
