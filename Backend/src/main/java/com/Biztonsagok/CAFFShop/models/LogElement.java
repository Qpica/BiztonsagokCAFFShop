package com.Biztonsagok.CAFFShop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LogElement {
	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	private UUID id;
	private String message;
	private Date date;
}
