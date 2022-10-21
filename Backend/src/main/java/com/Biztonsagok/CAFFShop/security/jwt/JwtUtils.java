package com.Biztonsagok.CAFFShop.security.jwt;

import com.Biztonsagok.CAFFShop.security.services.UserDetailsImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtUtils {
	@Value("${com.Biztonsagok.CAFFShop.jwtSecret}")
	private String jwtSecret;

	@Value("${com.Biztonsagok.CAFFShop.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {

		} catch (MalformedJwtException e) {

		} catch (ExpiredJwtException e) {

		} catch (UnsupportedJwtException e) {

		} catch (IllegalArgumentException e) {

		}

		return false;
	}
}
