package com.Biztonsagok.CAFFShop.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "userRole")
public class UserRole {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;

	@Enumerated(EnumType.STRING)
	private UserRoleType roleName;

	public UserRole() {
	}

	public UserRole(UserRoleType roleName) {
		this.roleName = roleName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserRoleType getRoleName() {
		return roleName;
	}

	public void setRoleName(UserRoleType roleName) {
		this.roleName = roleName;
	}
}
