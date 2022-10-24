package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.security.service.dto.AuthUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuthenticationFacade {
    public Optional<AuthUserDetails> getCurrentUserFromContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(it -> it instanceof UsernamePasswordAuthenticationToken)
                .map(it -> (AuthUserDetails) it.getPrincipal());
    }

    public Optional<UUID> getCurrentUserId() {
        return getCurrentUserFromContext()
                .map(AuthUserDetails::userId);
    }
}
