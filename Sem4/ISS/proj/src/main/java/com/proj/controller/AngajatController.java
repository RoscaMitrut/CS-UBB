package com.proj.controller;

import com.proj.domain.Angajat;
import com.proj.domain.Comanda;
import com.proj.domain.Produs;
import com.proj.observer.Observer;
import com.proj.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class AngajatController implements Observer {
	private Service service;
	private Angajat client;

	@FXML
	private TextField textComanda;
	@FXML
	private TableView<Produs> produseView;
	@FXML
	private TableColumn<Produs,String> numeprodusColoana;
	@FXML
	private TableColumn<Produs,Double> pretColoana;
	@FXML
	private TableColumn<Produs,Integer> stocColoana;
	ObservableList<Produs> produsModel = FXCollections.observableArrayList();

	public void setService(Service service, Angajat client) throws Exception {
		this.service = service;
		this.client = client;
		service.addObserver(this);
		populateProduseView();
	}
	@FXML
	private void initialize(){
		numeprodusColoana.setCellValueFactory(new PropertyValueFactory<Produs, String>("NumeProdus"));
		pretColoana.setCellValueFactory(new PropertyValueFactory<Produs,Double>("Pret"));
		stocColoana.setCellValueFactory(new PropertyValueFactory<Produs,Integer>("Cantitate"));

		produseView.setItems(produsModel);
	}

	private void populateProduseView() throws Exception {
		produsModel.clear();
		Iterable<Produs> produse = service.getProduse();
		produse.forEach(produs->produsModel.add(produs));
	}
	@Override
	public void update() {
		try {
			populateProduseView();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	public void handleComandaProdus(MouseEvent event){
		if(event.getClickCount() == 2) {
			Integer cantitate;
			Produs produs_selectat;

			try {
				produs_selectat = produseView.getSelectionModel().getSelectedItem();
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Selecteaza un produs!");
				alert.showAndWait();
				return;
			}
			try {
				cantitate = Integer.valueOf(textComanda.getText());
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Introdu cantitatea!");
				alert.showAndWait();
				return;
			}
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmare");
			alert.setContentText("Doriti sa cumparati " + cantitate +" "+ produs_selectat.getNumeProdus() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (!result.isPresent())
				return;
			else if (result.get() == ButtonType.CANCEL)
				return;
			else if (result.get() == ButtonType.OK) {
				try {
					Comanda comanda = new Comanda(produs_selectat.getId(), cantitate, produs_selectat.getPret());
					service.comanda(comanda);
					service.notifyObservers();
				} catch (Exception e) {
					Alert alert2 = new Alert(Alert.AlertType.ERROR);
					alert2.setContentText(e.getMessage());
					alert2.showAndWait();
				}
			}
		}
	}
}
