package com.Biztonsagok.CAFFShop.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class UserRole {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;

	@Enumerated(EnumType.STRING)
	private UserRoleType role_name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserRoleType getRole_name() {
		return role_name;
	}

	public void setRole_name(UserRoleType role_name) {
		this.role_name = role_name;
	}
}
