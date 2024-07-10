package project.network.jsonprot;


import project.model.Excursie;
import project.network.dto.BiletDto;
import project.network.dto.ExcursieDto;
import project.network.dto.UserDto;

import java.util.Arrays;

public class Request {
	private RequestType type;
	private UserDto user;
	private BiletDto bilet;
	private ExcursieDto excursie;
	private Integer oramin;
	private Integer oramax;

	public Request(){}
	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public BiletDto getBilet() {
		return bilet;
	}

	public void setBilet(BiletDto bilet) {
		this.bilet = bilet;
	}

	public ExcursieDto getExcursie() {
		return excursie;
	}
	public void setExcursie(ExcursieDto excursie) {
		this.excursie = excursie;
	}
	public void setOramax(Integer oramax) {
		this.oramax = oramax;
	}
	public Integer getOramax(){
		return oramax;
	}
	public void setOramin(Integer oramin) {
		this.oramin = oramin;
	}
	public Integer getOramin(){
		return oramin;
	}
	@Override
	public String toString() {
		return "Request{" +
				"type=" + type +
				", user=" + user +
				", bilet=" + bilet +
				", excursie=" + excursie +
				", oramin=" + oramin +
				", oramax="+oramax+
				'}';
	}
}
