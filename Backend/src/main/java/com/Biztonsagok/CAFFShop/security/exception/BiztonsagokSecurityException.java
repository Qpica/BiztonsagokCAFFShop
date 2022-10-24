package com.Biztonsagok.CAFFShop.security.exception;

import com.Biztonsagok.CAFFShop.BiztonsagokException;
import org.springframework.http.HttpStatus;

public class BiztonsagokSecurityException extends BiztonsagokException {

    public BiztonsagokSecurityException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}
