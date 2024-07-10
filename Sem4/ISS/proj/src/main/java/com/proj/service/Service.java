package com.proj.service;

import com.proj.domain.Angajat;
import com.proj.domain.Comanda;
import com.proj.domain.Produs;
import com.proj.observer.ObservableImplementat;
import com.proj.observer.Observer;
import com.proj.repo.ComandaRepo;
import com.proj.repo.ProdusRepo;
import com.proj.repo.AngajatRepo;

import java.util.List;
import java.util.Objects;

public class Service extends ObservableImplementat {
	private AngajatRepo angajatRepo;
	private ComandaRepo comandaRepo;
	private ProdusRepo produsRepo;

	public Service(AngajatRepo angajatRepo, ComandaRepo comandaRepo, ProdusRepo produsRepo) {
		this.angajatRepo = angajatRepo;
		this.comandaRepo = comandaRepo;
		this.produsRepo = produsRepo;
	}
	public List<Produs> getProduse() throws Exception {
		try{
			return produsRepo.getAll();
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}
	public Angajat getAngajat(String username, String password) throws Exception {
		if((Objects.equals(username, "") || (Objects.equals(password, "")))){
			throw new Exception("Date incomplete");
		}
		for(Angajat a: angajatRepo.getAll()){
			if(a.getUsername().equals(username) && a.getPassword().equals(password))
				return a;
		}
		throw new Exception("Date incorecte");
	}




	public List<Comanda> getComenzi() throws Exception {
		try{
			return comandaRepo.getAll();
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}


	public List<Angajat> getUseri() throws Exception {
		try{
		return angajatRepo.getAll();
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}



	public void comanda(Comanda comanda) throws Exception{
		try{
			if(comanda.getCantitate() <= produsRepo.checkStoc(comanda.getIdProdus())){
				comandaRepo.add(comanda);
				produsRepo.updateCantitate(comanda.getIdProdus(), comanda.getCantitate());
			}else{
				throw new Exception("Stoc insuficient");
			}
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}

	public void stergeProd(Integer id) throws Exception {
		try {
			produsRepo.delete(id);
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}

	public void modificaProd(Integer id,String nume_nou,Double pret_nou,Integer cantitate_noua) throws Exception {
		try{
		produsRepo.update(id,nume_nou,pret_nou,cantitate_noua);
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}

	public void adaugaProd(Produs produs) throws Exception {
		try{
		produsRepo.add(produs);
			}catch (Exception e){
		throw new Exception("Error "+e);
		}
	}

	public void stergeUser(Integer id) throws Exception {
		try{
		angajatRepo.delete(id);
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}

	public void modificaUser(Integer id,String username, String password, String nume) throws Exception {
		try{
		angajatRepo.update(id,username,password,nume);
		}catch (Exception e){
			throw new Exception("Error "+e);
		}
	}

	public void adaugaUser(Angajat user) throws Exception {
	try{
		angajatRepo.add(user);
	}catch (Exception e){
		throw new Exception("Error "+e);
	}
	}


}
