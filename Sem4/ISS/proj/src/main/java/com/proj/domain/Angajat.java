package com.proj.domain;

public class Angajat implements Entity<Integer>{
	public String username;
	private String password;
	private String nume;
	private Integer id;

	public Angajat(String username, String password, String nume) {
		this.username = username;
		this.password = password;
		this.nume = nume;
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

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
}
