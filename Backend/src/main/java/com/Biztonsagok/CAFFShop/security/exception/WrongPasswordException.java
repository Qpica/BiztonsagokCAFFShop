package com.Biztonsagok.CAFFShop.security.exception;

public class WrongPasswordException extends BiztonsagokSecurityException {
    public WrongPasswordException() {
        super("Wrong password!");
    }
}
