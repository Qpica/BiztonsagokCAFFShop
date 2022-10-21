package com.Biztonsagok.CAFFShop.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
public class CaffPicture {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;

	@NotBlank
	@Size(max = 40)
	private String title;

	@NotBlank
	@Size(max = 255)
	private String description;

	@Lob
	private byte[] caffPictureData;

	@OneToMany(mappedBy = "caffPicture")
	private List<UserComment> userCommentList;

	@ManyToOne
	@JoinColumn(name = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User owner;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public byte[] getCaffPictureData() {
		return caffPictureData;
	}

	public void setCaffPictureData(byte[] caffPictureData) {
		this.caffPictureData = caffPictureData;
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