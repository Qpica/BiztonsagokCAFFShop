package com.Biztonsagok.CAFFShop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCommentRequestDTO {
	@NotNull
	private String comment_value;
}
