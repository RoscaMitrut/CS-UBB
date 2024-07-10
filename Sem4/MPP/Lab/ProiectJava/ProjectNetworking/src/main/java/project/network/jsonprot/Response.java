package project.network.jsonprot;

import project.network.dto.BiletDto;
import project.network.dto.ExcursieDto;
import project.network.dto.UserDto;

import java.io.Serializable;
import java.util.Arrays;


public class Response implements Serializable {
	private ResponseType type;
	private String errorMessage;
	private UserDto user;
	private ExcursieDto[] excursii;
	private BiletDto bilet;

	public Response() {
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public ExcursieDto[] getExcursii() {
		return excursii;
	}

	public void setExcursii(ExcursieDto[] excursii) {
		this.excursii = excursii;
	}

	public BiletDto getBilet() {
		return bilet;
	}

	public void setBilet(BiletDto bilet) {
		this.bilet = bilet;
	}

	@Override
	public String toString() {
		return "Response{" +
				"type=" + type +
				", errorMessage='" + errorMessage + '\'' +
				", user=" + user +
				", excursie=" + Arrays.toString(excursii) +
				", bilet=" + bilet +
				'}';
	}
}
