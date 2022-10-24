package com.Biztonsagok.CAFFShop.security.service.dto;

import lombok.Builder;

@Builder
public record ValidateRefreshTokenResult(String username) {
}
