package com.proj.domain;

public class Comanda implements Entity<Integer>{
	private Integer id;
	private Integer idProdus;
	private Integer cantitate;
	private Double pretBucata;

	public Comanda(Integer idProdus, Integer cantitate, Double pretBucata) {
		this.idProdus = idProdus;
		this.cantitate = cantitate;
		this.pretBucata = pretBucata;
	}

	public Integer getIdProdus() {
		return idProdus;
	}

	public void setIdProdus(Integer idProdus) {
		this.idProdus = idProdus;
	}

	public Integer getCantitate() {
		return cantitate;
	}

	public void setCantitate(Integer cantitate) {
		this.cantitate = cantitate;
	}

	public Double getPretBucata() {
		return pretBucata;
	}

	public void setPretBucata(Double pretBucata) {
		this.pretBucata = pretBucata;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public Double getValoare(){
		return this.pretBucata * this.cantitate;
	}
}
