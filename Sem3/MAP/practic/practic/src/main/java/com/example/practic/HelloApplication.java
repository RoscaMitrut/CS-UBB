package com.example.practic;

import com.example.practic.controller.LoginController;
import com.example.practic.repo.ClientRepo;
import com.example.practic.repo.FlightRepo;
import com.example.practic.repo.TicketRepo;
import com.example.practic.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
	Service service;

	@Override
	public void start(Stage primaryStage) throws IOException {
		String url = "jdbc:postgresql://localhost:5432/practic";
		String username = "postgres";
		String password = "postgres";
		this.service = new Service(new ClientRepo(url,username,password),
				new FlightRepo(url,username,password),
				new TicketRepo(url,username,password));

		primaryStage.setTitle("START PAGE");
		startView(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


	private void startView(Stage stage)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/practic/loginView.fxml"));
		AnchorPane Layout = fxmlLoader.load();
		stage.setScene(new Scene(Layout));

		LoginController startController = fxmlLoader.getController();
		startController.setService(service);
	}
}