package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.narcano.jni.CAFF;
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
	private CAFF caffData;
	private int height;
	private int width;
	public CaffPictureResponseDTO(CaffPicture caffPicture) {
		this.title = caffPicture.getTitle();
		this.description = caffPicture.getDescription();
		this.price = caffPicture.getPrice();
	}
}
