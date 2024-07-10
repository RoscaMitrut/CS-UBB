package project.server;

import project.model.Bilet;
import project.model.Excursie;
import project.model.User;
import project.persistence.BiletRepository;
import project.persistence.ExcursieRepository;
import project.persistence.UserRepository;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;
import utils.Crypt;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectServiceImpl implements IService {

	private UserRepository userRepository;
	private ExcursieRepository excursieRepository;
	private BiletRepository biletRepository;
	private Map<Integer, IObserver> loggedClients;

	public ProjectServiceImpl(UserRepository userRepo, ExcursieRepository excursieRepo, BiletRepository biletRepo) {
		userRepository = userRepo;
		excursieRepository = excursieRepo;
		biletRepository = biletRepo;
		loggedClients = new ConcurrentHashMap<>();
	}

	@Override
	public synchronized void login(User user, IObserver client) throws ProjectException {
		User userR=userRepository.findUser(user.getUsername(),Crypt.decrypt(user.getPassword()));
		System.out.println(userR.getId());
		if (userR.getId()!=0){
			System.out.println(loggedClients.get(user.getId()));
			//PLACEHOLDER
			//if(loggedClients.get(user.getId())!=null)
			//	throw new ProjectException("User already logged in.");
			loggedClients.put(user.getId(), client);
		}else
			throw new ProjectException("Authentication failed.");

	}
	@Override
	public void logout(User user, IObserver client) throws ProjectException{
		IObserver localClient=loggedClients.remove(user.getId());
		if (localClient==null)
			throw new ProjectException("User "+user.getId()+" is not logged in.");
	}
	private final int defaultThreadsNo=5;

	private void notifyBought(){
		for(IObserver client: loggedClients.values()){
			try {
				client.ticketBought();
			}catch (ProjectException e){
				System.err.println("Error notifying agency "+e);
			}
		}
	}

	@Override
	public Excursie[] getExcursiiLaLocSiOra(Excursie excursie, Integer oraMin, Integer oraMax, IObserver client) throws ProjectException {
		LocalDateTime dataCuTimp = excursie.getOraPlecare();
		LocalDate data = dataCuTimp.toLocalDate();
		LocalDateTime dataMin = data.atTime(oraMin,0);
		LocalDateTime dataMax = data.atTime(oraMax,0);
		String obiectiv = excursie.getObiectivVizitat();

		try{
			List<Excursie> excursii;
			excursii = (List<Excursie>) excursieRepository.findExcursiiLaLocSiOra(obiectiv,dataMin,dataMax);
			Excursie[] excursiiarray = new Excursie[excursii.size()];
			excursiiarray = excursii.toArray(excursiiarray);
			return excursiiarray;
		}catch (Exception e){
			throw new ProjectException("Error "+ e);
		}
	}

	@Override
	public void rezerva(Bilet bilet , IObserver client) throws ProjectException {
		try {
			if (bilet.getNumarPersoane() <= excursieRepository.checkLocuriDisponibile(bilet.getIdExcursie())) {
				biletRepository.add(bilet);
				excursieRepository.updateLocuriDisponibile(bilet.getIdExcursie(), bilet.getNumarPersoane());
				notifyBought();
			}else{
				throw new ProjectException("Not enough seats");
			}
		}catch (Exception e){
			throw new ProjectException("Error "+e);
		}
	}

	@Override
	public Excursie[] getExcursii(IObserver client) throws ProjectException {
		try{
			List<Excursie> excursii;
			excursii = (List<Excursie>) excursieRepository.findAll();
			Excursie[] excursiiarray = new Excursie[excursii.size()];
			excursiiarray = excursii.toArray(excursiiarray);
			return excursiiarray;
		}catch (Exception e){
			throw new ProjectException("Error "+e);
		}
	}



}
