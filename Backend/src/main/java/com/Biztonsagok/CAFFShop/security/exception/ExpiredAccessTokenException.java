package com.Biztonsagok.CAFFShop.security.exception;

public class ExpiredAccessTokenException extends BiztonsagokSecurityException {
    public ExpiredAccessTokenException() {
        super("Token is expired!");
    }
}
