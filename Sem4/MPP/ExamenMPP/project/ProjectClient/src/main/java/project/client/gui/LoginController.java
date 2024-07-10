package project.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import project.model.User;
import project.services.IService;
import project.services.ProjectException;


public class LoginController {
	private IService service;
	private User crtUser;

	private LoggedInController loggedCtrl;
	@FXML
	private TextField usernameField;

	Parent mainParent;

	public void setService(IService service){this.service=service;}

	public void setParent(Parent p){
		mainParent = p;
	}

	public void handleLogin(ActionEvent actionEvent) {
		String username = usernameField.getText();
		crtUser = new User(username);
		try {
			service.login(crtUser, loggedCtrl);

			Stage stage = new Stage();
			stage.setTitle("Project Window for " + crtUser.getAlias());
			stage.setScene(new Scene(mainParent));

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					loggedCtrl.logout();
					System.exit(0);
				}
			});

			stage.show();
			loggedCtrl.setUser(crtUser);
			((Node) (actionEvent.getSource())).getScene().getWindow().hide();

			loggedCtrl.handleRefresh();

		}catch (ProjectException e){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Proj");
			alert.setHeaderText("Auth failure");
			alert.setContentText("Wrong user");
			alert.showAndWait();
		}

	}
	public void setLoggedController(LoggedInController loggedController){
		this.loggedCtrl = loggedController;
	}
}
