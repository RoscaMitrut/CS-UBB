package ro.mpp2024.service;
/*
import ro.mpp2024.repo.BiletRepository;
import ro.mpp2024.repo.ExcursieRepository;
import ro.mpp2024.repo.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Service implements IService{
	private UserRepository userRepo;
	private ExcursieRepository excursieRepo;
	private BiletRepository biletRepo;

	public Service(UserRepository userRepo, ExcursieRepository excursieRepo, BiletRepository biletRepo) {
		this.userRepo = userRepo;
		this.excursieRepo = excursieRepo;
		this.biletRepo = biletRepo;
	}

	public Iterable<Excursie> getExcursii(){return excursieRepo.findAll();}
	public Iterable<Bilet> getBilete(){return biletRepo.findAll();}
	public Iterable<User> getUseri(){return userRepo.findAll();}

	public User getUser(String username,String password){
		return userRepo.findUser(username,password);}

	public Iterable<Excursie> getExcursiiLaLocSiOra(String obiectiv, LocalDate data,Integer oraMin, Integer oraMax){
		LocalDateTime dataMin = data.atTime(oraMin,0);
		LocalDateTime dataMax = data.atTime(oraMax,0);
		return excursieRepo.findExcursiiLaLocSiOra(obiectiv,dataMin,dataMax);
	}
	public boolean rezerva(Excursie excursie,String numeClient,Integer nrTelefon,Integer nrLocuri){
		if(nrLocuri<=excursie.getLocuriDisponibile()){
			Bilet bilet = new Bilet(nrLocuri,numeClient,nrTelefon,excursie.getId());
			biletRepo.add(bilet);
			excursieRepo.updateLocuriDisponibile(excursie.getId(),nrLocuri);
			return true;
		}else {
				return false;
			}//THROW
	}
}
*/