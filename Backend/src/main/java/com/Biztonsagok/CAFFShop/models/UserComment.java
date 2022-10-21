package com.Biztonsagok.CAFFShop.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
	private CaffPicture caffPicture;

	@ManyToOne
	@JoinColumn(name = "users_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
