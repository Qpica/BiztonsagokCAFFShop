package com.Biztonsagok.CAFFShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
