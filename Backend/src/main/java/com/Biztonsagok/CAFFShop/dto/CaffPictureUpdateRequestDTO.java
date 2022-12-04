package com.Biztonsagok.CAFFShop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaffPictureUpdateRequestDTO {
	private String title;
	private String description;
	private String ownerUserName;
	private int price;

}
