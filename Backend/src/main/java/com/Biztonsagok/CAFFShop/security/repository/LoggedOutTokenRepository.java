package com.Biztonsagok.CAFFShop.security.repository;

import com.Biztonsagok.CAFFShop.security.domain.LoggedOutToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Set;

public interface LoggedOutTokenRepository extends CrudRepository<LoggedOutToken, String> {

    Set<LoggedOutToken> findAll();

    @Modifying
    void deleteByExpirationDateBefore(Instant expirationDate);
}
