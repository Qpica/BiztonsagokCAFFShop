package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "userRole")
public class UserRole {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	@JsonIgnore
	private UUID id;

	@Enumerated(EnumType.STRING)
	private UserRoleType roleName;

	public UserRole(UserRoleType roleName) {
		this.roleName = roleName;
	}
}
