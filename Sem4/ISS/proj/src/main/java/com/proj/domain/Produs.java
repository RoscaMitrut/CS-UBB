package com.proj.domain;

public class Produs implements Entity<Integer>{
	private Integer id;
	private String numeProdus;
	private Double pret;
	private Integer cantitate;

	public Produs(String numeProdus, Double pret, Integer cantitate) {
		this.numeProdus = numeProdus;
		this.pret = pret;
		this.cantitate = cantitate;
	}

	public String getNumeProdus() {
		return numeProdus;
	}

	public void setNumeProdus(String numeProdus) {
		this.numeProdus = numeProdus;
	}

	public Double getPret() {
		return pret;
	}

	public void setPret(Double pret) {
		this.pret = pret;
	}

	public Integer getCantitate() {
		return cantitate;
	}

	public void setCantitate(Integer cantitate) {
		this.cantitate = cantitate;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

}
