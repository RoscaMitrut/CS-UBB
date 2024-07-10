package project.services;

import project.model.Joc;
import project.model.User;

import java.util.List;

public interface IService{
	void login(User user, IObserver client) throws ProjectException;
	void logout(User user,IObserver client) throws ProjectException;

	GameStatus select(User user, String input);

	Joc getGame(User user);

	Integer getPoints(User user);

	List<Joc> getRanking();
}
