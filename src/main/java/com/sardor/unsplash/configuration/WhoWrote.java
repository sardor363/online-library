package com.sardor.unsplash.configuration;

import com.sardor.unsplash.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class WhoWrote implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Get current user
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            User employee = (User) authentication.getPrincipal();
            return Optional.ofNullable(employee);
        }
        return Optional.empty();

    }
}