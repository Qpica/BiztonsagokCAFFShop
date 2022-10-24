package com.Biztonsagok.CAFFShop.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LoggedOutToken {
    @Id
    @Column(length = 36)
    private String jti;

    @Column(nullable = false)
    private Instant expirationDate;
}
