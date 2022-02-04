package br.com.urso.security.payload.request;

import br.com.urso.security.oauth2.entity.AuthProvider;

import javax.validation.constraints.NotBlank;

public class Oauth2Request {
	@NotBlank
	private String username;
	private AuthProvider provider;
	private String password;
	private String name;
	private String surname;
	private String idProvider;

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

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdProvider() {
		return idProvider;
	}

	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}
}
