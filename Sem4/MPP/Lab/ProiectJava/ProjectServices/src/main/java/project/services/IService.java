package project.services;


import project.model.Bilet;
import project.model.Excursie;
import project.model.User;

public interface IService {
	Excursie[] getExcursii(IObserver client) throws ProjectException;
	void login(User user,IObserver client) throws ProjectException;
	void logout(User user,IObserver client) throws ProjectException;
	Excursie[] getExcursiiLaLocSiOra(Excursie excursie, Integer oraMin, Integer oraMax,IObserver client) throws ProjectException;
	void rezerva(Bilet bliet,IObserver client) throws ProjectException;
}
