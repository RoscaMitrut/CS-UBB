package com.example.practic.domain;

public class Client extends Entity<Long>{
	private String username;
	private String name;

	public Client(Long l, String username, String name) {
		super(l);
		this.username = username;
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
