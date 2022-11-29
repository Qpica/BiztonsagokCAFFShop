package com.Biztonsagok.CAFFShop.security.controller;

import com.Biztonsagok.CAFFShop.security.controller.dto.*;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		final var tokens = authenticationService.login(loginRequest.username(), loginRequest.password());
		return LoginResponse.builder()
				.accessToken(tokens.accessToken())
				.refreshToken(tokens.refreshToken())
				.build();
	}

	@PostMapping("/register")
	public void register(@RequestBody RegisterRequest registerRequest) {
		authenticationService.register(registerRequest.username(), registerRequest.password());
	}

	@PostMapping("/refresh")
	public RefreshTokenResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		final var tokens = authenticationService.refresh(refreshTokenRequest.refreshToken());
		return RefreshTokenResponse.builder()
				.accessToken(tokens.accessToken())
				.refreshToken(tokens.refreshToken())
				.build();
	}

	@PostMapping("/logout")
	public void logout() {
		authenticationService.logout();
	}

	@PostMapping("/force-logout")
	public void forceLogout() {
		authenticationService.forceLogout();
	}

}
