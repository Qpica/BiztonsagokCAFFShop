package com.Biztonsagok.CAFFShop.security.service.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record AuthUserDetails(UUID userId, String username, String jti, Instant expiration) {
}
