package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.UserRole;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private Set<UserRole> roles = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
}
