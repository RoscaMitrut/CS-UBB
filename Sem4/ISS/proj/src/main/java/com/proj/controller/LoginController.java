package com.proj.controller;

import com.proj.domain.Angajat;
import com.proj.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {
	public static boolean isBetween(Integer x, Integer low, Integer high){
		return low <= x && x <= high;
	}

	private Service service;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	public void setService(Service service){
		this.service=service;
	}
	public void handleLogin(){
		try{
			String username = usernameField.getText();
			String password = passwordField.getText();

			System.out.println(username);

			Angajat client = service.getAngajat(username,password);

			if(isBetween(client.getId(),1,9999)){

				URL fxmlLocation = getClass().getResource("/com/proj/AngajatView.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle(client.getUsername());
				AngajatController clientController = fxmlLoader.getController();
				clientController.setService(service, client);

				stage.show();
			}else if(isBetween(client.getId(),10000,10010)){
				URL fxmlLocation = getClass().getResource("/com/proj/AdminView.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle(client.getUsername());
				AdminController clientController = fxmlLoader.getController();
				clientController.setService(service, client);

				stage.show();
			}
		}catch (Exception e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
