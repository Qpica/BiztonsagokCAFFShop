package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserComment;

import java.util.List;

public class CaffPictureResponseDTO {
	private String title;
	private String description;
	private List<UserComment> userCommentList;
	private User owner;

	public CaffPictureResponseDTO(){ }
	public CaffPictureResponseDTO(String title, String description, List<UserComment> userCommentList, User owner) {
		this.title = title;
		this.description = description;
		this.userCommentList = userCommentList;
		this.owner = owner;
	}

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

	public List<UserComment> getUserCommentList() {
		return userCommentList;
	}

	public void setUserCommentList(List<UserComment> userCommentList) {
		this.userCommentList = userCommentList;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}