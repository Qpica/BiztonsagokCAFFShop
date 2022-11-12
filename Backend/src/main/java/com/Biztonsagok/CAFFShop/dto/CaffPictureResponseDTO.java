package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CaffPictureResponseDTO extends RepresentationModel<CaffPictureResponseDTO> {
	private String title;
	private String description;
	private List<UserComment> userCommentList;
	private User owner;
	private int price;

	public CaffPictureResponseDTO(CaffPicture caffPicture) {
		this.title = caffPicture.getTitle();
		this.description = caffPicture.getDescription();
		this.userCommentList = caffPicture.getUserCommentList();
		this.owner = caffPicture.getOwner();
	}

	public CaffPictureResponseDTO(String title, String description, List<UserComment> userCommentList, User owner) {
		this.title = title;
		this.description = description;
		this.userCommentList = userCommentList;
		this.owner = owner;
	}
}
