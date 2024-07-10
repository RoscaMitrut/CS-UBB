package com.example.practic.service;

import com.example.practic.domain.Client;
import com.example.practic.domain.Flight;
import com.example.practic.domain.Ticket;
import com.example.practic.observer.ObservableImplementat;
import com.example.practic.repo.ClientRepo;
import com.example.practic.repo.FlightRepo;
import com.example.practic.repo.TicketRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class Service extends ObservableImplementat {
	private ClientRepo clientRepo;
	private FlightRepo flightRepo;
	private TicketRepo ticketRepo;

	public Service(ClientRepo clientRepo, FlightRepo flightRepo, TicketRepo ticketRepo) {
		this.clientRepo = clientRepo;
		this.flightRepo = flightRepo;
		this.ticketRepo = ticketRepo;
	}

	public List<Client> getClienti(){return clientRepo.getAll();}
	public List<Flight> getFlights(){return flightRepo.getAll();}
	public List<Ticket> getTickets(){return ticketRepo.getAll();}

	public Client getClientByUsername(String username){
		for(Client c:clientRepo.getAll())
			if(c.getUsername().equals(username))
				return c;
		return null;
	}

	public void adaugaTicket(String username, Long idFlight){
		Random rand = new Random();
		Long id = rand.nextLong();
		Ticket ticket=new Ticket(id,username,idFlight, LocalDateTime.now());
		ticketRepo.adauga(ticket);
	}
}
