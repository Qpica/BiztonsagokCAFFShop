package com.Biztonsagok.CAFFShop.security.service.dto;

import lombok.Builder;

@Builder
public record TokenResult(String accessToken, String refreshToken) {
}
