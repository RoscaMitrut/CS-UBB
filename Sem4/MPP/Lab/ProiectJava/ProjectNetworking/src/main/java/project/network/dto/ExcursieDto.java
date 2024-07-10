package project.network.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExcursieDto implements Serializable {
	private String obiectivVizitat;
	private String oraPlecare;
	private String firmaTransport;
	private Double pret;
	private int locuriDisponibile;
	private int id;

	public ExcursieDto(String obiectivVizitat, LocalDateTime oraPlecare, String firmaTransport, Double pret, int locuriDisponibile, int id) {
		this.obiectivVizitat = obiectivVizitat;
		this.oraPlecare = oraPlecare.toString();
		this.firmaTransport = firmaTransport;
		this.pret = pret;
		this.locuriDisponibile = locuriDisponibile;
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExcursieDto["  + obiectivVizitat +
				 oraPlecare +
				 firmaTransport +
				 pret +
				 locuriDisponibile +
				 id +
				']';
	}

	public String getObiectivVizitat() {
		return obiectivVizitat;
	}

	public void setObiectivVizitat(String obiectivVizitat) {
		this.obiectivVizitat = obiectivVizitat;
	}

	public LocalDateTime getOraPlecare() {
		return LocalDateTime.parse(oraPlecare);
	}

	public void setOraPlecare(LocalDateTime oraPlecare) {
		this.oraPlecare = oraPlecare.toString();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
