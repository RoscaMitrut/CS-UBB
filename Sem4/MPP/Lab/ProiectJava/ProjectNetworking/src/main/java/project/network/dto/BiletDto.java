package project.network.dto;

import java.io.Serializable;

public class BiletDto implements Serializable {
	private int numarPersoane;
	private String numeClient;
	private int numarTelefonClient;
	private int idExcursie;
	private int id;

	public BiletDto(int numarPersoane, String numeClient, int numarTelefonClient, int idExcursie, int id) {
		this.numarPersoane = numarPersoane;
		this.numeClient = numeClient;
		this.numarTelefonClient = numarTelefonClient;
		this.idExcursie = idExcursie;
		this.id = id;
	}

	@Override
	public String toString() {
		return "BiletDto[" +
				 numarPersoane +
				 numeClient +
				 numarTelefonClient +
				 idExcursie +
				 id +
				']';
	}

	public int getNumarPersoane() {
		return numarPersoane;
	}

	public void setNumarPersoane(int numarPersoane) {
		this.numarPersoane = numarPersoane;
	}

	public String getNumeClient() {
		return numeClient;
	}

	public void setNumeClient(String numeClient) {
		this.numeClient = numeClient;
	}

	public int getNumarTelefonClient() {
		return numarTelefonClient;
	}

	public void setNumarTelefonClient(int numarTelefonClient) {
		this.numarTelefonClient = numarTelefonClient;
	}

	public int getIdExcursie() {
		return idExcursie;
	}

	public void setIdExcursie(int idExcursie) {
		this.idExcursie = idExcursie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
