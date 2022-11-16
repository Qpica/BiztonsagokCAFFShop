package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserComment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaffPictureResponseDTO extends RepresentationModel<CaffPictureResponseDTO> {
	private String title;
	private String description;
	private List<UserCommentResponseDTO> userCommentList;
	private UserResponseDTO owner;
	private int price;

	public CaffPictureResponseDTO(CaffPicture caffPicture) {
		this.title = caffPicture.getTitle();
		this.description = caffPicture.getDescription();
		this.price = caffPicture.getPrice();
	}
}
