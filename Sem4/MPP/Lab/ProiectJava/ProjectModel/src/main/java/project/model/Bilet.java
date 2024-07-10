package project.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.lang.annotation.Annotation;

@Entity
@Table(name="Bilete")
public class Bilet implements project.model.Entity<Integer> {
	private int numarPersoane;
	private String numeClient;
	private int numarTelefonClient;
	private int idExcursie;
	private int id;

	public Bilet(int numarPersoane, String numeClient, int numarTelefonClient, int idExcursie) {
		this.numarPersoane = numarPersoane;
		this.numeClient = numeClient;
		this.numarTelefonClient = numarTelefonClient;
		this.idExcursie = idExcursie;
	}

	public Bilet() {
		this.numarPersoane=0;
		this.numeClient = null;
		this.numarTelefonClient=0;
		this.idExcursie=0;
		this.id=0;
	}

	@Column(name="numarPersoane")
	public int getNumarPersoane() {
		return numarPersoane;
	}

	@Column(name="numarPersoane")
	public void setNumarPersoane(int numarPersoane) {
		this.numarPersoane = numarPersoane;
	}

	@Column(name="numeClient")
	public String getNumeClient() {
		return numeClient;
	}

	@Column(name="numeClient")
	public void setNumeClient(String numeClient) {
		this.numeClient = numeClient;
	}

	@Column(name="numarTelefonClient")
	public int getNumarTelefonClient() {
		return numarTelefonClient;
	}

	@Column(name="numarTelefonClient")
	public void setNumarTelefonClient(int numarTelefonClient) {
		this.numarTelefonClient = numarTelefonClient;
	}

	@Column(name="idExcursie")
	public int getIdExcursie() {
		return idExcursie;
	}
	@Column(name="idExcursie")
	public void setIdExcursie(int idExcursie) {
		this.idExcursie = idExcursie;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	@Id
	@GeneratedValue(generator = "increment")
	public Integer getId() {
		return id;
	}
}
