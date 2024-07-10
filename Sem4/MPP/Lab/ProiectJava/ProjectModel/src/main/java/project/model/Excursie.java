package project.model;

import java.time.LocalDateTime;

public class Excursie implements Entity<Integer> {
	private String obiectivVizitat;
	private LocalDateTime oraPlecare;
	private String firmaTransport;
	private Double pret;
	private int locuriDisponibile;
	private int id;

	public Excursie(String obiectivVizitat, LocalDateTime oraPlecare, String firmaTransport, Double pret, int locuriDisponibile) {
		this.obiectivVizitat = obiectivVizitat;
		this.oraPlecare = oraPlecare;
		this.firmaTransport = firmaTransport;
		this.pret = pret;
		this.locuriDisponibile = locuriDisponibile;
	}

	public Excursie() {
		this.id=0;
		this.obiectivVizitat = null;
		this.oraPlecare = null;
		this.firmaTransport = null;
		this.pret = null;
		this.locuriDisponibile = 0;
	}

	public String getObiectivVizitat() {
		return obiectivVizitat;
	}

	public void setObiectivVizitat(String obiectivVizitat) {
		this.obiectivVizitat = obiectivVizitat;
	}

	public LocalDateTime getOraPlecare() {
		return oraPlecare;
	}

	public void setOraPlecare(LocalDateTime oraPlecare) {
		this.oraPlecare = oraPlecare;
	}

	public String getFirmaTransport() {
		return firmaTransport;
	}

	public void setFirmaTransport(String firmaTransport) {
		this.firmaTransport = firmaTransport;
	}

	public Double getPret() {
		return pret;
	}

	public void setPret(Double pret) {
		this.pret = pret;
	}

	public int getLocuriDisponibile() {
		return locuriDisponibile;
	}

	public void setLocuriDisponibile(int locuriDisponibile) {
		this.locuriDisponibile = locuriDisponibile;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Excursie{" +
				"obiectivVizitat='" + obiectivVizitat + '\'' +
				", oraPlecare=" + oraPlecare +
				", firmaTransport='" + firmaTransport + '\'' +
				", pret=" + pret +
				", locuriDisponibile=" + locuriDisponibile +
				", id=" + id +
				'}';
	}
}
