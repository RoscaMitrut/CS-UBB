package project.server;

import org.apache.logging.log4j.*;
import project.model.Configuratii;
import project.model.Joc;
import project.model.User;
import project.services.GameStatus;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;
import project.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectServiceImpl implements IService {
	private UserRepository userRepo;
	private JocRepository repo2;
	private ConfiguratiiRepository repo3;
	private Map<String, IObserver> loggedClients;
	private Map<String, Joc> games;

	private static final Logger logger = LogManager.getLogger();

	public ProjectServiceImpl(UserRepository r1, JocRepository r2, ConfiguratiiRepository r3) {
		logger.traceEntry("Init ProjectServiceImpl");
		userRepo = r1;
		repo2 = r2;
		repo3 = r3;
		loggedClients = new ConcurrentHashMap<>();
		games = new ConcurrentHashMap<>();
		logger.traceExit();
	}
	@Override
	public synchronized void login(User user, IObserver client) throws ProjectException {
		logger.traceEntry("Handling Login (alias: {})", user.getAlias());

		var result = userRepo.findOne(user.getAlias());

		if (result.isEmpty()) {
			logger.traceExit("Player with alias {} not found", user.getAlias());
			throw new ProjectException("Auth error");
		}
		loggedClients.put(result.get().getAlias(), client);
		logger.traceExit();
	}

	@Override
	public void logout(User user, IObserver client) throws ProjectException{
		logger.traceEntry("logout");
		IObserver localClient=loggedClients.remove(user.getAlias());
		if (localClient==null)
			throw new ProjectException("User "+user.getAlias()+" is not logged in.");
		logger.traceExit();
	}
	private final int defaultThreadsNo=5;

	private void notifyAction(){
		for(IObserver client: loggedClients.values()){
			try{
				client.action();
			}catch (ProjectException e){
				System.err.println("Error notifying "+e);
			}
		}
	}

	@Override
	public synchronized GameStatus select(User user, String input){
		logger.traceEntry();

		Joc game = games.get(user.getAlias());

		if(game == null){
			games.put(user.getAlias(), new Joc());
			game = games.get(user.getAlias());
			int id_curent = user.getAlias().charAt(user.getAlias().length() - 1) - '0';
			//int id_curent_int = id_curent - '0';
			game.setIdJucator(id_curent);
			game.setPuncteObtinute(0);

			Iterable<Configuratii> configuratii = repo3.findAll();
			String configuratie = configuratii.iterator().next().getConfiguratie();
			// not random, currently (first)
			game.setConfiguratie(configuratie);
		}

		game.setConfiguratePropusa(game.getConfiguratePropusa() + input + ',');

		if(game.getConfiguratePropusa().chars().filter(ch->ch==',').count() > 4){
			repo2.add(game);
			games.put(user.getAlias(),game);
			notifyAction();
			logger.traceExit();
			return GameStatus.LOSE;
            }

		String[] cuvinte = game.getConfiguratie().split(",");

		for (String cuvant:cuvinte) {
			if(Objects.equals(input, cuvant)){
				game.setPuncteObtinute(game.getPuncteObtinute() + input.length());
			}
		}
		Integer punctaj_total = 0;

		for (String cuvant:cuvinte){
			punctaj_total += cuvant.length();
		}

		if(game.getPuncteObtinute()>=punctaj_total){
			repo2.add(game);
			//game = gameRepository.findMyLastGame(player).orElse(null); presupun ca pt id
			games.put(user.getAlias(),game);
			notifyAction();
			logger.traceExit();
			return GameStatus.WIN;
		}

		logger.traceExit();
		return GameStatus.CONTINUE;
	}

	@Override
	public Joc getGame(User user){
		logger.traceEntry();
		var game = games.get(user.getAlias());
		logger.traceExit();
		return game;
	}

	@Override
	public Integer getPoints(User user){
		logger.traceEntry();
		var game = games.get(user.getAlias());
		logger.traceExit();
		return game.getPuncteObtinute();
	}

	@Override
	public List<Joc> getRanking() {
		logger.traceEntry();
		List<Joc> list = (List<Joc>) repo2.findAll();
		logger.traceExit();
		return list;
	}

}
