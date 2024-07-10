package project.model;

public class Bilet_bak implements Entity<Integer> {
	private int numarPersoane;
	private String numeClient;
	private int numarTelefonClient;
	private int idExcursie;
	private int id;

	public Bilet_bak(int numarPersoane, String numeClient, int numarTelefonClient, int idExcursie) {
		this.numarPersoane = numarPersoane;
		this.numeClient = numeClient;
		this.numarTelefonClient = numarTelefonClient;
		this.idExcursie = idExcursie;
	}

	public Bilet_bak() {
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

	@Override
	public void setId(Integer integer) {
		this.id=id;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
