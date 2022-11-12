package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

	@NotNull
	@Lob
	private byte[] caffPictureData;

	@OneToMany(mappedBy = "caffPicture")
	@JsonManagedReference
	private List<UserComment> userCommentList;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "users_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private User owner;

	private int price;

	public CaffPicture(String title, String description, byte[] caffPictureData, List<UserComment> userCommentList, User owner) {
		this.title = title;
		this.description = description;
		this.caffPictureData = caffPictureData;
		this.userCommentList = userCommentList;
		this.owner = owner;
	}
}
