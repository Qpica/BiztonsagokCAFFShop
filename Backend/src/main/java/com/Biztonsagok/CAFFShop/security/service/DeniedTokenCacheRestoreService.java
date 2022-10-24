package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.security.domain.LoggedOutToken;
import com.Biztonsagok.CAFFShop.security.repository.LoggedOutTokenRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeniedTokenCacheRestoreService {

    private final LoggedOutTokenRepository loggedOutTokenRepository;

    public ConcurrentMap<String, LoggedOutToken> restoreCache() {
        return loggedOutTokenRepository.findAll().stream().collect(Collectors.toConcurrentMap(LoggedOutToken::getJti, Function.identity()));
    }
}
