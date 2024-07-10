package project.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "jocuri")
public class Joc implements Serializable {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	@Basic
	@Column(name = "id_jucator")
	private int idJucator;
	@Basic
	@Column(name = "puncte_obtinute")
	private int puncteObtinute;
	@Basic
	@Column(name = "configuratie")
	private String configuratie;
	@Basic
	@Column(name = "configurate_propusa")
	private String configuratePropusa;

	public Joc() {
		this.configuratePropusa = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdJucator() {
		return idJucator;
	}

	public void setIdJucator(int idJucator) {
		this.idJucator = idJucator;
	}

	public int getPuncteObtinute() {
		return puncteObtinute;
	}

	public void setPuncteObtinute(int puncteObtinute) {
		this.puncteObtinute = puncteObtinute;
	}

	public String getConfiguratie() {
		return configuratie;
	}

	public void setConfiguratie(String configuratie) {
		this.configuratie = configuratie;
	}

	public String getConfiguratePropusa() {
		return configuratePropusa;
	}

	public void setConfiguratePropusa(String configuratePropusa) {
		this.configuratePropusa = configuratePropusa;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Joc joc = (Joc) o;
		return id == joc.id && idJucator == joc.idJucator && puncteObtinute == joc.puncteObtinute && Objects.equals(configuratie, joc.configuratie) && Objects.equals(configuratePropusa, joc.configuratePropusa);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idJucator, puncteObtinute, configuratie, configuratePropusa);
	}
}
