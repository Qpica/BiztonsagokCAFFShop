package com.Biztonsagok.CAFFShop.security.exception;

public class InvalidTokenException extends BiztonsagokSecurityException {
    public InvalidTokenException() {
        super("Token is invalid!");
    }
}
