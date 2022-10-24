package com.Biztonsagok.CAFFShop.security.service;

import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.security.exception.UserNotFoundException;
import com.Biztonsagok.CAFFShop.security.exception.WrongPasswordException;
import com.Biztonsagok.CAFFShop.security.service.dto.LoginResult;
import com.Biztonsagok.CAFFShop.security.service.dto.RefreshTokenResult;
import com.Biztonsagok.CAFFShop.services.UserService;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final IssuedTokenService issuedTokenService;
    private final DenyTokenService denyTokenService;

    private final UserService userService;


    public LoginResult login(String username, String password) {
        final var user = findUserOrThrow(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            final var tokens = tokenService.generateTokens(user);
            return LoginResult.builder()
                    .accessToken(tokens.accessToken())
                    .refreshToken(tokens.refreshToken())
                    .build();
        } else {
            throw new WrongPasswordException();
        }
    }

    public void register(String username, String password) {
        userService.createUser(username, passwordEncoder.encode(password));
    }

    public RefreshTokenResult refresh(String refreshToken) {
        final var validateRefreshTokenResult = tokenService.validateRefreshToken(refreshToken);
        final var user = findUserOrThrow(validateRefreshTokenResult.username());
        final var tokens = tokenService.generateTokens(user);
        return RefreshTokenResult.builder()
                .accessToken(tokens.accessToken())
                .refreshToken(tokens.refreshToken())
                .build();
    }

    public void forceLogout() {
        authenticationFacade.getCurrentUserId()
                .map(issuedTokenService::getIssuedTokensForUser)
                .stream()
                .flatMap(Set::stream)
                .forEach(denyTokenService::denyToken);
    }

    public void logout() {
        authenticationFacade.getCurrentUserFromContext()
                .map(it -> issuedTokenService.findTokenById(it.jti()))
                .ifPresent(token -> {
                    denyTokenService.denyToken(token);
                    denyTokenService.denyToken(issuedTokenService.findRefreshTokenForAccessToken(token.getJti()));
                });
    }

    private User findUserOrThrow(String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user registered with username: " + username));
    }
}
