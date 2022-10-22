package com.Biztonsagok.CAFFShop.dto;

import com.Biztonsagok.CAFFShop.models.CaffPicture;
import com.Biztonsagok.CAFFShop.models.UserComment;
import com.Biztonsagok.CAFFShop.models.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserResponseDTO {
	private String userName;
	private Set<UserRole> roles = new HashSet<>();
	private List<CaffPicture> caffs;
	private List<UserComment> comments;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public List<CaffPicture> getCaffs() {
		return caffs;
	}

	public void setCaffs(List<CaffPicture> caffs) {
		this.caffs = caffs;
	}

	public List<UserComment> getComments() {
		return comments;
	}

	public void setComments(List<UserComment> comments) {
		this.comments = comments;
	}
}
