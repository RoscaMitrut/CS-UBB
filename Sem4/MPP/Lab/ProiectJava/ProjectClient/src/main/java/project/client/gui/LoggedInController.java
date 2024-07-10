package project.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.model.Bilet;
import project.model.Excursie;
import project.model.User;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable, IObserver {
	private IService service;
	private User user;

	@FXML
	private TextField obiectivCautareField;
	@FXML
	private TextField oraMinCautareField;
	@FXML
	private TextField oraMaxCautareField;
	@FXML
	private DatePicker dataCautareDatePicker;
	@FXML
	private Button cautareButon;
	@FXML
	private Button refreshButon;
	@FXML
	private TextField numeClientRezervareField;
	@FXML
	private TextField numarTelefonRezervareField;
	@FXML
	private TextField numarLocuriRezervareField;

	@FXML
	private TableView<Excursie> excursiiView;
	@FXML
	private TableColumn<Excursie,String> excursiiObiectivColoana;
	@FXML
	private TableColumn<Excursie,String> excursiiFirmaTransportColoana;
	@FXML
	private TableColumn<Excursie, LocalDateTime> excursiiPlecareColoana;
	@FXML
	private TableColumn<Excursie,Double> excursiiPretColoana;
	@FXML
	private TableColumn<Excursie,Integer> excursiiLocuriDisponibileColoana;
	ObservableList<Excursie> excursiiModel = FXCollections.observableArrayList();

	@FXML
	private TableView<Excursie> cautareView;
	@FXML
	private TableColumn<Excursie,String> cautareFirmaTransportColoana;
	@FXML
	private TableColumn<Excursie, LocalDateTime> cautarePlecareColoana;
	@FXML
	private TableColumn<Excursie,Double> cautarePretColoana;
	@FXML
	private TableColumn<Excursie,Integer> cautareLocuriDisponibileColoana;

	ObservableList<Excursie> cautareModel = FXCollections.observableArrayList();

	public void setUser(User user){this.user=user;}
	public void setServer(IService server){
		this.service = server;
		System.out.println("contructor LoggedInController cu server param");
		initializeViews();
	}
	public void setService(IService service, User user) throws ProjectException {
		this.service = service;
		this.user = user;
		//service.addObserver(this);
		populateExcursiiModel();
	}
	public void initializeViews(){
		excursiiObiectivColoana.setCellValueFactory(new PropertyValueFactory<Excursie, String>("obiectivVizitat"));
		excursiiFirmaTransportColoana.setCellValueFactory(new PropertyValueFactory<Excursie, String>("firmaTransport"));
		excursiiLocuriDisponibileColoana.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuriDisponibile"));
		excursiiPlecareColoana.setCellValueFactory(new PropertyValueFactory<Excursie,LocalDateTime>("oraPlecare"));
		excursiiPretColoana.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));

		cautareFirmaTransportColoana.setCellValueFactory(new PropertyValueFactory<Excursie,String>("firmaTransport"));
		cautarePretColoana.setCellValueFactory(new PropertyValueFactory<Excursie,Double>("pret"));
		cautareLocuriDisponibileColoana.setCellValueFactory(new PropertyValueFactory<Excursie,Integer>("locuriDisponibile"));
		cautarePlecareColoana.setCellValueFactory(new PropertyValueFactory<Excursie,LocalDateTime>("oraPlecare"));

		dataCautareDatePicker.setValue(LocalDate.now());

		excursiiView.setItems(excursiiModel);
		cautareView.setItems(cautareModel);
	}

	private void populateExcursiiModel() throws ProjectException {
		excursiiModel.clear();
		Excursie[] excursii = service.getExcursii(this);
		List<Excursie> excursiiList = Arrays.stream(excursii).toList();
		excursiiList.forEach(excursie->excursiiModel.add(excursie));
	}

	@FXML
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void ticketBought() {
		Platform.runLater(()->{
			try {
				populateExcursiiModel();
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

	public void handleRefresh(){
		try {
			populateExcursiiModel();
		} catch (ProjectException e) {
			throw new RuntimeException(e);
		}
	}
	public void handleCauta(){
		try {
			String obiectiv = obiectivCautareField.getText();
			LocalDate data = dataCautareDatePicker.getValue();
			Integer oraMin = Integer.valueOf(oraMinCautareField.getText());
			Integer oraMax = Integer.valueOf(oraMaxCautareField.getText());

			Excursie exc = new Excursie();
			exc.setObiectivVizitat(obiectiv);
			exc.setOraPlecare(data.atStartOfDay());
			cautareModel.clear();
			Excursie[] excursii = service.getExcursiiLaLocSiOra(exc,oraMin,oraMax,this);
			List<Excursie> excursiiList = Arrays.stream(excursii).toList();
			excursiiList.forEach(excursie->cautareModel.add(excursie));
		}catch (Exception ex){
			System.out.println(ex.getMessage());
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Va rugam sa introduceti date valide");
			alert.showAndWait();
		}
	}
	@FXML
	public void clickItemExcursii(MouseEvent event){
		if(event.getClickCount() == 2){
			Excursie excursie = excursiiView.getSelectionModel().getSelectedItem();
			String numeClient;
			Integer nrTelefon;
			Integer nrLocuri;
			try{
				numeClient = numeClientRezervareField.getText();
				nrTelefon = Integer.valueOf(numarTelefonRezervareField.getText());
				nrLocuri = Integer.valueOf(numarLocuriRezervareField.getText());
			}catch (Exception e){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Va rugam sa introduceti date valide");
				alert.showAndWait();
				return;
			}
			System.out.println(numeClient);
			System.out.println(nrTelefon);
			System.out.println(nrLocuri);
			System.out.println(excursie.getId());
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmare");
			alert.setContentText("Doriti sa rezervati "+nrLocuri+" la "+excursie.getObiectivVizitat()+" pe numele "+numeClient+" si numarul de telefon "+nrTelefon+"?");
			Optional<ButtonType> result = alert.showAndWait();
			if(!result.isPresent())
				return;
			else if(result.get() == ButtonType.CANCEL)
				return;
			else if(result.get() == ButtonType.OK){
				try{
					Bilet bilet = new Bilet(nrLocuri,numeClient,nrTelefon,excursie.getId());
					service.rezerva(bilet,this);
					populateExcursiiModel();
					cautareModel.clear();
				}catch (Exception e){
					Alert alert2 = new Alert(Alert.AlertType.ERROR);
					alert2.setContentText(e.getMessage());
					alert2.showAndWait();
				}
			}
		}
	}
	@FXML
	public void clickItemCauta(MouseEvent event){
		if(event.getClickCount() == 2){
			Excursie excursie = cautareView.getSelectionModel().getSelectedItem();
			String numeClient;
			Integer nrTelefon;
			Integer nrLocuri;
			try{
				numeClient = numeClientRezervareField.getText();
				nrTelefon = Integer.valueOf(numarTelefonRezervareField.getText());
				nrLocuri = Integer.valueOf(numarLocuriRezervareField.getText());
			}catch (Exception e){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Va rugam sa introduceti date valide");
				alert.showAndWait();
				return;
			}
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmare");
			alert.setContentText("Doriti sa rezervati "+nrLocuri+" bilet(e) la "+excursie.getObiectivVizitat()+" pe numele "+numeClient+" si numarul de telefon "+nrTelefon+"?");
			Optional<ButtonType> result = alert.showAndWait();
			if(!result.isPresent())
			return;
			else if(result.get() == ButtonType.CANCEL)
			return;
			else if(result.get() == ButtonType.OK){
				try{
					Bilet bilet = new Bilet(nrLocuri,numeClient,nrTelefon,excursie.getId());
					service.rezerva(bilet,this);
					populateExcursiiModel();
					cautareModel.clear();
				}catch (Exception e){
					Alert alert2 = new Alert(Alert.AlertType.ERROR);
					alert2.setContentText(e.getMessage());
					alert2.showAndWait();
				}
			}
		}
	}


}
