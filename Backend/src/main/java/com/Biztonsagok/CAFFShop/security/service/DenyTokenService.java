package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.security.domain.IssuedToken;
import com.Biztonsagok.CAFFShop.security.domain.LoggedOutToken;
import com.Biztonsagok.CAFFShop.security.repository.LoggedOutTokenRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentMap;

@Service
public class DenyTokenService {

    private final LoggedOutTokenRepository loggedOutTokenRepository;
    private final ConcurrentMap<String, LoggedOutToken> cache;

    public DenyTokenService(LoggedOutTokenRepository loggedOutTokenRepository, DeniedTokenCacheRestoreService deniedTokenCacheRestoreService) {
        this.loggedOutTokenRepository = loggedOutTokenRepository;
        cache = deniedTokenCacheRestoreService.restoreCache();
    }

    public void denyToken(IssuedToken issuedToken) {
        final var loggedOutToken = new LoggedOutToken(issuedToken.getJti(), issuedToken.getExpirationDate());
        addToCache(loggedOutToken);
        saveToDatabase(loggedOutToken);
    }

    public boolean isDenied(String jti) {
        return cache.containsKey(jti);
    }

    public boolean isNotDenied(String jti) {
        return !isDenied(jti);
    }

    public void clearExpiredTokensFromCache() {
        loggedOutTokenRepository.deleteByExpirationDateBefore(Instant.now());
        cache.entrySet().removeIf(e -> e.getValue().getExpirationDate().isBefore(Instant.now()));
    }

    private void addToCache(LoggedOutToken loggedOutToken) {
        cache.put(loggedOutToken.getJti(), loggedOutToken);
    }

    private void saveToDatabase(LoggedOutToken loggedOutToken) {
        loggedOutTokenRepository.save(loggedOutToken);
    }
}
