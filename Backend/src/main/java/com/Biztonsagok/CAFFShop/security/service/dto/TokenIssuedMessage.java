package com.Biztonsagok.CAFFShop.security.service.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TokenIssuedMessage(java.util.UUID userId, String accessTokenJti, String refreshTokenJti,
								 Instant accessTokenExpirationDate, Instant refreshTokenExpirationDate) {
}
