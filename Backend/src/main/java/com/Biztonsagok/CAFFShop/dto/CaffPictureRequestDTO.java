package com.Biztonsagok.CAFFShop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class CaffPictureRequestDTO {
	private String title;
	private String description;
	private MultipartFile caffFile;
	private String ownerUserName;
	private int price;
}
