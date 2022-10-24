package com.Biztonsagok.CAFFShop.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecretService {
    private final String secret;

    public SecretService(@Value("${com.Biztonsagok.CAFFShop.jwtSecret}") String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
