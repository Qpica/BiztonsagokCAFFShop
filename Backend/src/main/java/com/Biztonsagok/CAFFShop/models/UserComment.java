package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class UserComment {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;

	@NotBlank
	private String comment_value;

	@ManyToOne
	@JoinColumn(name = "caffpicture_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private CaffPicture caffPicture;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "users_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private User owner;

	public UserComment() {
	}

	public UserComment(String comment_value) {
		this.comment_value = comment_value;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getComment_value() {
		return comment_value;
	}

	public void setComment_value(String comment_value) {
		this.comment_value = comment_value;
	}

	public CaffPicture getCaffPicture() {
		return caffPicture;
	}

	public void setCaffPicture(CaffPicture caffPicture) {
		this.caffPicture = caffPicture;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
