package project.model;


import utils.Crypt;

import java.io.Serializable;

public class User implements Serializable,Entity<Integer> {
	private String username;
	private String password;
	private int id;

	public User(String username, String password) {
		this.username = username;
		this.password = Crypt.encrypt(password);
	}
	public User() {
		this.username = null;
		this.password = null;
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

	//Only use if the password you set is already encrypted
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
