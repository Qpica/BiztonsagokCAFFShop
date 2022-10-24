package com.Biztonsagok.CAFFShop.security.config;

import com.Biztonsagok.CAFFShop.security.service.JwtTokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.Biztonsagok.CAFFShop.security.service.TokenService.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtTokenFilter extends BasicAuthenticationFilter {

	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	public JwtTokenFilter(AuthenticationManager authenticationManager, JwtTokenAuthenticationService jwtTokenAuthenticationService) {
		super(authenticationManager);
		this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws IOException, ServletException {

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		final var token = resolveToken(request);
		return jwtTokenAuthenticationService.getAuthenticationFromToken(token);
	}

	private String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}

		return null;
	}

}