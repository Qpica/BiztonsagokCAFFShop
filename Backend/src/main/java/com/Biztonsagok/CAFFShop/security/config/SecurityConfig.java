package com.Biztonsagok.CAFFShop.security.config;

import com.Biztonsagok.CAFFShop.security.service.JwtTokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_URL = "/api/auth/login";
	private static final String TOKEN_REFRESH_URL = "/api/auth/refresh";
	private static final String REGISTER_URL = "/api/users/register";
	private final JwtTokenAuthenticationService jwtTokenAuthenticationService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.POST, TOKEN_REFRESH_URL).permitAll()
				.antMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(
						new JwtTokenFilter(authenticationManager(), jwtTokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class
				)
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.logout().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication();
	}
}

