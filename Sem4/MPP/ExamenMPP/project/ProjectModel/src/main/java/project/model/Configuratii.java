package project.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "configuratii")
public class Configuratii implements Serializable {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	@Basic
	@Column(name = "configuratie")
	private String configuratie;

	public Configuratii(String in) {
		this.configuratie=in;
	}

	public Configuratii() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConfiguratie() {
		return configuratie;
	}

	public void setConfiguratie(String configuratie) {
		this.configuratie = configuratie;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Configuratii that = (Configuratii) o;
		return id == that.id && Objects.equals(configuratie, that.configuratie);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, configuratie);
	}
}
