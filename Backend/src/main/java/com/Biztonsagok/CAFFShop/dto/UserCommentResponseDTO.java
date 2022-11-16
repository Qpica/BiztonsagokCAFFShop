package com.Biztonsagok.CAFFShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UserCommentResponseDTO {
	@NotNull
	private String comment_value;
	@NotNull
	private String ownerName;
}
