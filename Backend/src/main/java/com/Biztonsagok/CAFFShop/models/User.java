package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	@JsonIgnore
	private UUID id;

	@NotBlank
	@Column(unique=true)
	private String username;

	@NotBlank
	@JsonIgnore
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "userRole_id"))
	@JsonIgnore
	private Set<UserRole> roles = new HashSet<>();

	@OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	@JsonIgnore
	private List<CaffPicture> caffs;

	@OneToMany(mappedBy = "owner")
	@JsonManagedReference
	@JsonIgnore
	private List<UserComment> comments;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
