package com.am.sort.api.security.entity;



import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.am.sort.api.security.enums.ProfileEnum;


@Document
public class User {
	
	@Id
	private String id;
	
	@Indexed( unique = true)
	@NotBlank(message = "Requer um E-mail Valido")
	@Email(message = "E-mail Invalido")
	private String email;
	
	
	@NotBlank(message = "Senha Requerida")
	@Size(min = 6)
	private String password;
	
	private ProfileEnum profile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}
	

}
