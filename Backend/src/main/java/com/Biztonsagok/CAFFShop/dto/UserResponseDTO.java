package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {
	private String userName;
	private Set<UserRole> roles = new HashSet<>();

	public UserResponseDTO(Optional<User> user) {
		this.userName = user.get().getUsername();
		this.roles = user.get().getRoles();
	}
}
