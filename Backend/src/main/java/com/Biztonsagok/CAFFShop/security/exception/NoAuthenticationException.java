package com.Biztonsagok.CAFFShop.security.exception;

public class NoAuthenticationException extends BiztonsagokSecurityException {
    public NoAuthenticationException() {
        super("No authentication was found!");
    }
}
