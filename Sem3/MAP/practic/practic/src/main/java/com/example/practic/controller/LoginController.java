package com.example.practic.controller;

import com.example.practic.HelloApplication;
import com.example.practic.domain.Client;
import com.example.practic.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private Service service;
	@FXML
	private TextField usernameField;
	@FXML
	private Button loginButton;

	public void setService(Service service){
		this.service=service;
	}

	public void handleLogin(ActionEvent actionEvent){
		try{
			String username = usernameField.getText();

			Client client = service.getClientByUsername(username);

			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/practic/clientView.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle(client.getUsername());
			ClientController clientController = fxmlLoader.getController();
			clientController.setService(service, client);

			stage.show();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}