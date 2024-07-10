package com.proj;

import com.proj.controller.LoginController;
import com.proj.repo.ComandaRepo;
import com.proj.repo.ProdusRepo;
import com.proj.repo.AngajatRepo;
import com.proj.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;

import java.io.IOException;

public class HelloApplication extends Application {
	Service service;

	@Override
	public void start(Stage primaryStage) throws IOException {
		String url = "jdbc:postgresql://localhost:5432/iss";
		String username = "postgres";
		String password = "postgres";
		this.service = new Service(new AngajatRepo(url, username, password),
				new ComandaRepo(url, username, password),
				new ProdusRepo(url, username, password));

		primaryStage.setTitle("START PAGE");
		startView(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void startView(Stage stage) throws IOException {
		URL fxmlLocation = getClass().getResource("/com/proj/LoginView.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlLocation);
		AnchorPane Layout = loader.load();
		stage.setScene(new Scene(Layout));

		LoginController startController = loader.getController();
		startController.setService(service);
	}
}