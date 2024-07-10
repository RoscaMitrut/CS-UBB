package com.example.practic.controller;

import com.example.practic.domain.Client;
import com.example.practic.domain.Flight;
import com.example.practic.domain.Ticket;
import com.example.practic.observer.Observer;
import com.example.practic.service.Service;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ClientController implements Observer {
	private Service service;
	private Client client;
	@FXML
	private DatePicker datePicker;

	@FXML
	private TableColumn<Flight, LocalDateTime> departureColoana;

	@FXML
	private ComboBox<String> fromCombo;

	@FXML
	private TableColumn<Flight, LocalDateTime> landingColoana;

	@FXML
	private TableColumn<Flight, Integer> seatsColoana;

	@FXML
	private TableView<Flight> tableView;

	@FXML
	private ComboBox<String> toCombo;

	@FXML
	private Button cautaButton;
	@FXML
	private Button buyButton;
	@FXML
	private TableColumn<Flight,Integer> availableColoana;
	@FXML
	private Button nextButton;

	ObservableList<Ticket> model2= FXCollections.observableArrayList();
	ObservableList<Ticket> model3= FXCollections.observableArrayList();

	private Integer index=5;
	ObservableList<Flight> model= FXCollections.observableArrayList();

	@FXML
	private TableView<Ticket> tableViewBileteAchizitionate;
	@FXML
	private TableColumn<Ticket, Long> achizitionateColoana;

	@FXML
	private TableView<Ticket> tableViewBileteVandute;
	@FXML
	private TableColumn<Ticket, Long> vanduteColoana;

	public void setService(Service service, Client client){
		this.service = service;
		this.client = client;
		initModel();
		service.addObserver(this);
	}
	@FXML
	public void initialize() {

		landingColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingTime"));
		departureColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureTime"));
		seatsColoana.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

		availableColoana.setCellValueFactory(c -> {
			Flight fl=c.getValue();
			Integer cnt=0;
			for(Ticket t: service.getTickets())
			{
				if(t.getFlightId().equals(fl.getId()))
					cnt++;
			}
			return new ReadOnlyObjectWrapper<Integer>(fl.getSeats()-cnt);
		});

		//achizitionateColoana.setCellValueFactory(new PropertyValueFactory<Ticket,Long>("flightId"));
		//vanduteColoana.setCellValueFactory(new PropertyValueFactory<Ticket,Long>("flightId"));

		achizitionateColoana.setCellValueFactory(new PropertyValueFactory<Ticket,Long>("id"));
		vanduteColoana.setCellValueFactory(new PropertyValueFactory<Ticket,Long>("id"));



		tableView.setItems(model);
		tableViewBileteAchizitionate.setItems(model2);
		tableViewBileteVandute.setItems(model3);
	}

	private void initModel(){
		setCombo();
		handleSearch();
		handleTables();
	}

	public void handleTables(){
		model2.clear();
		for(Ticket t:service.getTickets()){
			if(t.getUsername().equals(this.client.getUsername())){
				model2.add(t);
			}
		}
		LocalDate ian_24 = LocalDate.of(2024,1,24);
		model3.clear();
		for(Ticket t:service.getTickets()){
			if(t.getPurchaseTime().toLocalDate().compareTo(ian_24)==0){
				model3.add(t);
			}
		}
	}

	public void setCombo(){
		Set<String> from = new HashSet<>();
		Set<String> to = new HashSet<>();
		for(Flight flight : service.getFlights()) {
			from.add(flight.getFrom());
			to.add(flight.getTo());
		}
		fromCombo.getItems().addAll(from);
		toCombo.getItems().addAll(to);
	}

	public void handleSearch() {
		LocalDate start = datePicker.getValue();
		String from = fromCombo.getValue();
		String to=toCombo.getValue();
		if(start!=null && from!=null && to!=null) {
			Integer localIndex=0;

			model.clear();
			for (Flight fl : service.getFlights()) {
				if (fl.getDepartureTime().toLocalDate().compareTo(start) == 0 && fl.getFrom().equals(from) && fl.getTo().equals(to)) {
					localIndex++;
					if(localIndex<=index && localIndex>index-5)
						model.add(fl);
				}
			}
		}
	}

	public void handleBuy(ActionEvent event) {
		Flight flight= tableView.getSelectionModel().getSelectedItem();
		service.adaugaTicket(client.getUsername(),flight.getId());

		service.notifyObservers();
	}
	@Override
	public void update() {
		initModel();
	}

	public void handleNext(ActionEvent event){
		index=index+5;
		handleSearch();
	}

	public void handlePrevious(ActionEvent event){
		index=index-5;
		handleSearch();
	}
}
