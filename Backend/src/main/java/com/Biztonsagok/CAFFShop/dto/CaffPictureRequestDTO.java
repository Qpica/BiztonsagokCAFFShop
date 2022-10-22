package com.Biztonsagok.CAFFShop.dto;

import org.springframework.web.multipart.MultipartFile;


public class CaffPictureRequestDTO {
	private String title;
	private String description;
	private MultipartFile caffFile;
	private String ownerUserName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getCaffFile() {
		return caffFile;
	}

	public void setCaffFile(MultipartFile caffFile) {
		this.caffFile = caffFile;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
}
