package project.services;

import project.model.Bilet;

public interface IObserver {
	void ticketBought() throws ProjectException;
}
