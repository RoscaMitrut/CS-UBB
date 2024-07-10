/*
package ro.mpp2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.mpp2024.controller.LoginController;
import ro.mpp2024.service.Service;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Main extends Application{
	Service service;
	@Override
	public void start(Stage primaryStage) throws Exception{
		try{
			URL fxmlLocation = getClass().getResource("/loginView.fxml");
			System.out.println(fxmlLocation);
			FXMLLoader loader = new FXMLLoader(fxmlLocation);
			AnchorPane layout = loader.load();
			LoginController startController = loader.getController();
			startController.setService(getService());

			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
			primaryStage.setTitle("LogIn");
			primaryStage.show();

		}catch (Exception e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error: "+e);
			alert.showAndWait();
		}
	}//LOGGING
	public static void main(String[] args) {
		launch(args);
	}

	static Service getService() throws Exception{
		try {
			Properties props=new Properties();
			props.load(new FileReader("bd.config"));
			BiletRepository biletRepo = new BiletDBRepository(props);
			ExcursieRepository excursieRepo = new ExcursieDBRepository(props);
			UserRepository userRepo = new UserDBRepository(props);
			return new Service(userRepo,excursieRepo,biletRepo);
		} catch (IOException e) {
			throw new Exception("Error "+e);
		}
	}
}
*/