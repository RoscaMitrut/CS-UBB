package project.network.dto;

import project.model.User;

import java.io.Serializable;

public class UserDto implements Serializable {
	private int id;
	private String password;
	private String username;

	public UserDto(int id,String username,String password){
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getPassword(){
		return password;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username =	username;
	}
	@Override
	public String toString() {
		return "UserDto["+id+' '+username+' '+password+']';
	}
}
