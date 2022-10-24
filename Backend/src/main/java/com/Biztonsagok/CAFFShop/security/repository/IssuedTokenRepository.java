package com.Biztonsagok.CAFFShop.security.repository;

import com.Biztonsagok.CAFFShop.security.domain.IssuedToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IssuedTokenRepository extends CrudRepository<IssuedToken, String> {

    Optional<IssuedToken> findByAccessTokenJti(String accessJti);

    Set<IssuedToken> findByUserId(UUID id);

    @Modifying
    void deleteByExpirationDateBefore(Instant instant);
}
