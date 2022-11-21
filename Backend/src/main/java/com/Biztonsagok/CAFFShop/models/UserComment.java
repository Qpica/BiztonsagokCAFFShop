package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserComment {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	@JsonIgnore
	private UUID id;

	@NotBlank
	private String comment_value;

	@ManyToOne
	@JoinColumn(name = "caffpicture_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	@JsonIgnore
	private CaffPicture caffPicture;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "users_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private User owner;

	public UserComment(String comment_value){
		this.comment_value = comment_value;
	}
}
