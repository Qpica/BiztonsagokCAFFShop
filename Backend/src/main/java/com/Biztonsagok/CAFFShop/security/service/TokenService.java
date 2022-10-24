package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.security.exception.InvalidTokenException;
import com.Biztonsagok.CAFFShop.security.service.dto.TokenIssuedMessage;
import com.Biztonsagok.CAFFShop.security.service.dto.TokenResult;
import com.Biztonsagok.CAFFShop.security.service.dto.ValidateRefreshTokenResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_ISSUER = "biztonsagok";
    public static final String CUSTOMER_ID_CLAIM = "userId";
    public static final String ACCESS_TOKEN_SUBJECT = "accesstoken";
    public static final String REFRESH_TOKEN_SUBJECT = "refreshtoken";

    private final SecretService secretService;
    private final DenyTokenService denyTokenService;
    private final IssuedTokenService issuedTokenService;

    public TokenResult generateTokens(User user) {
        final var accessToken = generateAccessToken(user);
        final var refreshToken = generateRefreshToken(user);

        issuedTokenService.tokenIssued(createTokenIssuedMessage(user, accessToken, refreshToken));

        return TokenResult.builder()
                .accessToken(accessToken.jwt())
                .refreshToken(refreshToken.jwt())
                .build();
    }

    private Token generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_SUBJECT, 2000000000);
    }

    private Token generateRefreshToken(User user) {
        return generateToken(user, REFRESH_TOKEN_SUBJECT, 2000000000);
    }

    private String randomJti() {
        return UUID.randomUUID().toString();
    }

    private Date getExpiration(int ttl) {
        return new Date(System.currentTimeMillis() + ttl);
    }

    //TODO: ttl
    private Token generateToken(User user, String subject, int ttl) {
        final var jti = randomJti();
        final var expiration = getExpiration(ttl);
        final var jwt = Jwts.builder()
                .setId(jti)
                .setSubject(subject)
                .setAudience(user.getUsername())
                .setIssuer(TOKEN_ISSUER)
                .claim(CUSTOMER_ID_CLAIM, user.getId())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secretService.getSecret())
                .compact();
        return new Token(jti, expiration, jwt);
    }

    private Claims validateToken(String subject, String refreshToken) {
        try {
            return Jwts.parser()
                    .requireIssuer(TOKEN_ISSUER)
                    .requireSubject(subject)
                    .setSigningKey(secretService.getSecret())
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private static TokenIssuedMessage createTokenIssuedMessage(User user, Token accessToken, Token refreshToken) {
        return TokenIssuedMessage.builder()
                .accessTokenJti(accessToken.jti())
                .accessTokenExpirationDate(accessToken.expirationDate().toInstant())
                .refreshTokenJti(refreshToken.jti())
                .refreshTokenExpirationDate(refreshToken.expirationDate().toInstant())
                .userId(user.getId())
                .build();
    }

    public ValidateRefreshTokenResult validateRefreshToken(String refreshToken) {
        final var claims = validateToken(REFRESH_TOKEN_SUBJECT, refreshToken);
        if (denyTokenService.isDenied(claims.getId())) {
            throw new BadCredentialsException(""); //TODO
        }
        return new ValidateRefreshTokenResult(claims.getAudience());
    }

    private record Token(String jti, Date expirationDate, String jwt) {
    }
}
