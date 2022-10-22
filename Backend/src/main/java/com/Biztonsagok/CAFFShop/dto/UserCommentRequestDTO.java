package com.Biztonsagok.CAFFShop.dto;

import javax.validation.constraints.NotNull;

public class UserCommentRequestDTO {
	@NotNull
	private String comment_value;

	public String getComment_value() {
		return comment_value;
	}

	public void setComment_value(String comment_value) {
		this.comment_value = comment_value;
	}
}
