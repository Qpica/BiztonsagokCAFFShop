package com.Biztonsagok.CAFFShop;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BiztonsagokException extends RuntimeException {

    private final HttpStatus status;

    public BiztonsagokException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
