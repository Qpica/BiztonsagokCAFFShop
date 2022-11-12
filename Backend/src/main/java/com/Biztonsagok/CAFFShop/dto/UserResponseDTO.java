package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.UserComment;
import com.Biztonsagok.CAFFShop.models.UserRole;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponseDTO {
	private String userName;
	private Set<UserRole> roles = new HashSet<>();
}
