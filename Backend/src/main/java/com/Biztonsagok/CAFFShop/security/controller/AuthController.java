package com.Biztonsagok.CAFFShop.security.controller;

import com.Biztonsagok.CAFFShop.security.controller.dto.*;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationFacade;
import com.Biztonsagok.CAFFShop.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthenticationService authenticationService;
	private final AuthenticationFacade authenticationFacade;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {

		log.info(MessageFormat.format("[{0}]::[Login Request]: User({1}) logged in!", LocalDateTime.now().toString(), loginRequest.username()));

		final var tokens = authenticationService.login(loginRequest.username(), loginRequest.password());
		return LoginResponse.builder()
				.accessToken(tokens.accessToken())
				.refreshToken(tokens.refreshToken())
				.build();
	}

	/*
	@PostMapping("/register")
	public void register(@RequestBody RegisterRequest registerRequest) {
		authenticationService.register(registerRequest.username(), registerRequest.password());

		log.info(MessageFormat.format("[{0}]::[User Registration]: User({1}) registered!", LocalDateTime.now().toString(), registerRequest.username()));

	}
	 */
	@PostMapping("/refresh")
	public RefreshTokenResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {

		log.info(MessageFormat.format("[{0}]::[{1}]: Refreshed Token!", LocalDateTime.now().toString(), authenticationFacade.getCurrentUserFromContext().get().username()));

		final var tokens = authenticationService.refresh(refreshTokenRequest.refreshToken());
		return RefreshTokenResponse.builder()
				.accessToken(tokens.accessToken())
				.refreshToken(tokens.refreshToken())
				.build();
	}

	@PostMapping("/logout")
	public void logout() {

		log.info(MessageFormat.format("[{0}]::[{1}]: Logged out!", LocalDateTime.now().toString(), authenticationFacade.getCurrentUserFromContext().get().username()));

		authenticationService.logout();
	}

	@PostMapping("/force-logout")
	public void forceLogout() {

		log.info(MessageFormat.format("[{0}]::[{1}]: Force-Logged out!", LocalDateTime.now().toString(), authenticationFacade.getCurrentUserFromContext().get().username()));

		authenticationService.forceLogout();
	}

}
