package com.Biztonsagok.CAFFShop.security.service.dto;

import lombok.Builder;

@Builder
public record LoginResult(String accessToken, String refreshToken) {
}
