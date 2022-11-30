package com.Biztonsagok.CAFFShop.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PurchaseElement {

	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;

	private UUID userId;
	private UUID caffId;

	public PurchaseElement(UUID userId, UUID caffId){
		this.userId = userId;
		this.caffId = caffId;
	}
}
