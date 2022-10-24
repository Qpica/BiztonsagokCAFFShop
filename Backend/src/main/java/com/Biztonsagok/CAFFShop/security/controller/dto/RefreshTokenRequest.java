package com.Biztonsagok.CAFFShop.security.controller.dto;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(String refreshToken) {
}
