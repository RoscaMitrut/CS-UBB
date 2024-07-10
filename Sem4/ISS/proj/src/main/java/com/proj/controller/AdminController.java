package com.proj.controller;

import com.proj.domain.Angajat;
import com.proj.domain.Comanda;
import com.proj.domain.Produs;
import com.proj.observer.Observer;
import com.proj.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.Optional;

public class AdminController implements Observer {
	@FXML
	private TextField fieldNumeUser;
	@FXML
	private TextField fieldPassword;
	@FXML
	private TextField fieldUsername;
	@FXML
	private TextField fieldNumeProd;
	@FXML
	private TextField fieldCantitate;
	@FXML
	private TextField fieldPret;
	private Service service;
	private Angajat client;

	@FXML
	private TableView<Comanda> tableViewStats;
	@FXML
	private TableColumn<Comanda,Double> valoareStatColoana;
	ObservableList<Comanda> statModel = FXCollections.observableArrayList();


	@FXML
	private TableView<Produs> tableViewProduse;
	@FXML
	private TableColumn<Produs,Integer> idProdusColoana;
	@FXML
	private TableColumn<Produs,String> numeProdusColoana;
	@FXML
	private TableColumn<Produs,Double> pretBucataProdusColoana;
	@FXML
	private TableColumn<Produs,Integer> cantitateProdusColoana;
	ObservableList<Produs> produsModel = FXCollections.observableArrayList();

	@FXML
	private TableView<Angajat> tableViewUseri;
	@FXML
	private TableColumn<Angajat, Integer> idUserColoana;
	@FXML
	private TableColumn<Angajat, String> usernameUserColoana;
	@FXML
	private TableColumn<Angajat, String> passwordUserColoana;
	@FXML
	private TableColumn<Angajat, String> numeUserColoana;
	ObservableList<Angajat> userModel = FXCollections.observableArrayList();

	public void setService(Service service, Angajat client) throws Exception {
		this.service = service;
		this.client = client;
		service.addObserver(this);
		update();
	}

	@FXML
	private void initialize(){
		idProdusColoana.setCellValueFactory(new PropertyValueFactory<Produs, Integer>("id"));
		numeProdusColoana.setCellValueFactory(new PropertyValueFactory<Produs, String>("numeProdus"));
		pretBucataProdusColoana.setCellValueFactory(new PropertyValueFactory<Produs,Double>("pret"));
		cantitateProdusColoana.setCellValueFactory(new PropertyValueFactory<Produs,Integer>("cantitate"));

		idUserColoana.setCellValueFactory(new PropertyValueFactory<Angajat,Integer>("id"));
		usernameUserColoana.setCellValueFactory(new PropertyValueFactory<Angajat,String>("username"));
		passwordUserColoana.setCellValueFactory(new PropertyValueFactory<Angajat,String>("password"));
		numeUserColoana.setCellValueFactory(new PropertyValueFactory<Angajat,String>("nume"));

		valoareStatColoana.setCellValueFactory(new PropertyValueFactory<Comanda,Double>("Valoare"));

		tableViewProduse.setItems(produsModel);
		tableViewUseri.setItems(userModel);
		tableViewStats.setItems(statModel);
	}

	private void populateStatView() throws Exception {
		statModel.clear();
		Iterable<Comanda> comenzi = service.getComenzi();
		comenzi.forEach(comanda -> statModel.add(comanda));
	}
	private void populateProduseView() throws Exception {
		produsModel.clear();
		Iterable<Produs> produse = service.getProduse();
		produse.forEach(produs->produsModel.add(produs));
	}
	private void populateUserView() throws Exception {
		userModel.clear();
		Iterable<Angajat> angajati = service.getUseri();
		angajati.forEach(user->userModel.add(user));
	}

	@Override
	public void update() {
		try {
			populateProduseView();
			populateUserView();
			populateStatView();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void handleStergeProd(){
		Produs produs_selectat = tableViewProduse.getSelectionModel().getSelectedItem();
		if (produs_selectat == null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selecteaza un produs!");
			alert.showAndWait();
			return;
		}
		try{
			service.stergeProd(produs_selectat.getId());
			service.notifyObservers();
		}catch (Exception e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(String.valueOf(e));
			alert.showAndWait();
			return;
		}
	}

	public void handleModificaProd() {
		String nume;
		Double pret;
		Integer cantitate;
		Produs produs_selectat = tableViewProduse.getSelectionModel().getSelectedItem();

		if (produs_selectat == null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selecteaza un produs!");
			alert.showAndWait();
			return;
		}

		try {
			nume = fieldNumeProd.getText();
			pret = Double.valueOf(fieldPret.getText());
			cantitate = Integer.valueOf(fieldCantitate.getText());
		}catch (Exception e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Completati campurile!");
			alert.showAndWait();
			return;
		}
		try{
			service.modificaProd(produs_selectat.getId(), nume, pret, cantitate);
			service.notifyObservers();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error" + e);
			alert.showAndWait();
			return;
		}
	}

	public void handleAdaugaProd(){
		String nume;
		Double pret;
		Integer cantitate;
		try {
			nume = fieldNumeProd.getText();
			pret = Double.valueOf(fieldPret.getText());
			cantitate = Integer.valueOf(fieldCantitate.getText());
		}catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Date incomplete/invalide");
			alert.showAndWait();
			return;
		}
		try{
			Produs prod = new Produs(nume,pret,cantitate);
			service.adaugaProd(prod);
			service.notifyObservers();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error");
			alert.showAndWait();
			return;
		}
	}

	public void handleStergeUser(){
		Angajat user = tableViewUseri.getSelectionModel().getSelectedItem();
		if(user==null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selectati campul");
			alert.showAndWait();
			return;
		}
		try{
		service.stergeUser(user.getId());
		service.notifyObservers();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error " + e);
			alert.showAndWait();
			return;
		}
	}

	public void handleModificaUser(){
		Angajat user = tableViewUseri.getSelectionModel().getSelectedItem();
		if(user==null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selectati campul");
			alert.showAndWait();
			return;
		}
		String username = fieldUsername.getText();
		String password = fieldPassword.getText();
		String nume = fieldNumeUser.getText();
		if (Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(nume, "")){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Introduceti date in campuri");
			alert.showAndWait();
			return;
		}

		try{
			service.modificaUser(user.getId(),username,password,nume);
			service.notifyObservers();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error");
			alert.showAndWait();
			return;
		}
	}

	public void handleAdaugaUser(){
		try{
			String username = fieldUsername.getText();
			String password = fieldPassword.getText();
			String nume = fieldNumeUser.getText();
			if (Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(nume, "")){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Introduceti date in campuri");
				alert.showAndWait();
				return;
			}

			Angajat user = new Angajat(username,password,nume);
			service.adaugaUser(user);
			service.notifyObservers();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error");
			alert.showAndWait();
			return;
		}
	}
	@FXML
	public void handleClickProdus(MouseEvent event){
		Produs produs_selectat = tableViewProduse.getSelectionModel().getSelectedItem();
		fieldCantitate.setText(String.valueOf(produs_selectat.getCantitate()));
		fieldNumeProd.setText(produs_selectat.getNumeProdus());
		fieldPret.setText(String.valueOf(produs_selectat.getPret()));
	}
	@FXML
	public void handleClickUser(MouseEvent event){
		Angajat user_selectat = tableViewUseri.getSelectionModel().getSelectedItem();
		fieldUsername.setText(user_selectat.getUsername());
		fieldPassword.setText(user_selectat.getPassword());
		fieldNumeUser.setText(user_selectat.getNume());
	}

}

