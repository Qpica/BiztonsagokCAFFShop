package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;

public class CaffPictureDataResponseDTO {
	private byte[] caffPictureData;

	public CaffPictureDataResponseDTO(){ }

	public CaffPictureDataResponseDTO(CaffPicture caffPicture) {
		this.caffPictureData = caffPicture.getCaffPictureData();
	}

	public byte[] getCaffPictureData() {
		return caffPictureData;
	}

	public void setCaffPictureData(byte[] caffPictureData) {
		this.caffPictureData = caffPictureData;
	}
}
