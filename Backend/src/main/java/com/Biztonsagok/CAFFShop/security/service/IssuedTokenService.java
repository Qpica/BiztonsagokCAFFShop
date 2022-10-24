package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.security.domain.IssuedToken;
import com.Biztonsagok.CAFFShop.security.exception.InconsistentDatabaseException;
import com.Biztonsagok.CAFFShop.security.repository.IssuedTokenRepository;
import com.Biztonsagok.CAFFShop.security.service.dto.TokenIssuedMessage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class IssuedTokenService {

    private final IssuedTokenRepository issuedTokenRepository;

    public Set<IssuedToken> getIssuedTokensForUser(UUID userId) {
        return issuedTokenRepository.findByUserId(userId);
    }

    public IssuedToken findTokenById(String jti) {
        return issuedTokenRepository.findById(jti)
                .orElseThrow(() -> new InconsistentDatabaseException("There is no token with jti: " + jti));
    }

    public IssuedToken findRefreshTokenForAccessToken(String accessTokenJti) {
        return issuedTokenRepository.findByAccessTokenJti(accessTokenJti)
                .orElseThrow(() -> new InconsistentDatabaseException("There is no refresh token for issued accessToken with jti: " + accessTokenJti));
    }

    public void removeExpiredTokens() {
        issuedTokenRepository.deleteByExpirationDateBefore(Instant.now());
    }

    public void tokenIssued(TokenIssuedMessage tokenIssuedMessage) {
        final var accessToken = IssuedToken.builder()
                .jti(tokenIssuedMessage.accessTokenJti())
                .expirationDate(tokenIssuedMessage.accessTokenExpirationDate())
                .userId(tokenIssuedMessage.userId())
                .build();
        issuedTokenRepository.save(accessToken);
        final var refreshToken = IssuedToken.builder()
                .jti(tokenIssuedMessage.refreshTokenJti())
                .expirationDate(tokenIssuedMessage.refreshTokenExpirationDate())
                .userId(tokenIssuedMessage.userId())
                .accessTokenJti(tokenIssuedMessage.accessTokenJti())
                .build();
        issuedTokenRepository.save(refreshToken);
    }

}
