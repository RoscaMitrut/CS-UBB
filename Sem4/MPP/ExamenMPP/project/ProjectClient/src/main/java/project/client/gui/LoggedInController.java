package project.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import project.model.User;
import project.services.GameStatus;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;
import project.model.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable, IObserver {
	@FXML
	public TextField userInput;
	@FXML
	private TableView<Joc> view;
	@FXML
	TableColumn<Joc, Integer> viewC1;
	@FXML
	TableColumn<Joc, String> viewC2;
	@FXML
	TableColumn<Joc, Integer> viewC3;
	@FXML
	public Label statusLabel;
	@FXML
	public Label pointsLabel;
	@FXML
	public Label lettersLabel;
	private IService service;
	private User user;

	ObservableList<Joc> model = FXCollections.observableArrayList();

	public void setServer(IService server){
		this.service = server;
		System.out.println("contructor LoggedInController cu server param");
		initializeViews();
		//populateModel();
	}
	public void setUser(User user){this.user=user;}
	public void initializeViews(){
		//aliasColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPlayer().getAlias()));
		viewC1.setCellValueFactory(new PropertyValueFactory<Joc, Integer>("IdJucator"));
		viewC2.setCellValueFactory(new PropertyValueFactory<Joc, String>("configuratie"));
		viewC3.setCellValueFactory(new PropertyValueFactory<Joc, Integer>("PuncteObtinute"));

		view.setItems(model);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	private void populateModel() throws ProjectException{
		model.clear();
		List<Joc> jocuri = service.getRanking();
		jocuri.forEach(joc -> model.add(joc));

	}
	@Override
	public void action(){
		Platform.runLater(()->{
			try {
				populateModel();
			} catch (ProjectException e) {
				e.printStackTrace();
			}
		});
	}

	void logout(){
		try{
			service.logout(user,this);
		}catch (ProjectException e){
			System.out.println("Logout error "+e);
		}
	}

	public void handleLitere(){
		String text = service.getGame(user).getConfiguratie();
		String litere = "";
		for (int i =0; i< text.length();i++){
			char c = text.charAt(i);
			if (!litere.contains(String.valueOf(c))) {
				litere = litere + c;
			}
		}
		lettersLabel.setText(litere.replace(',',' '));
	}
	public void handleRefresh(){
		try{
			populateModel();
		}catch (ProjectException e){
			throw new RuntimeException(e);
		}
	}

	public void handleMove(ActionEvent mouseEvent) {
		if (!Objects.equals(statusLabel.getText(), "CONTINUE")) return;

		String input = userInput.getText();

		var status = service.select(user,input);

		statusLabel.setText(status.toString());
		handleLitere();
		pointsLabel.setText(service.getPoints(user).toString());

		if (status == GameStatus.CONTINUE) return;

		var game = service.getGame(user);
		if (game == null) return;
	}
}
