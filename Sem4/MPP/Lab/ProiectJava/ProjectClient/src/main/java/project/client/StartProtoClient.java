package project.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.client.gui.LoggedInController;
import project.client.gui.LoginController;
import project.network.jsonprot.ProjectServicesProxy;
import project.network.protobuffprotocol.ProtoProjectProxy;
import project.services.IService;

import java.io.IOException;
import java.util.Properties;

public class StartProtoClient extends Application {
	private static int defaultChatPort=5555;
	private static String defaultServer="localhost";



	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("In start");
		Properties clientProps = new Properties();
		try {
			clientProps.load(StartJsonClient.class.getResourceAsStream("/projectclient.properties"));
			System.out.println("Client properties set. ");
			clientProps.list(System.out);
		} catch (IOException e) {
			System.err.println("Cannot find projectclient.properties " + e);
			return;
		}

		String serverIP = clientProps.getProperty("project.server.host", defaultServer);
		int serverPort = defaultChatPort;

		try {
			serverPort = Integer.parseInt(clientProps.getProperty("project.server.port"));
		} catch (NumberFormatException ex) {
			System.err.println("Wrong port number " + ex.getMessage());
			System.out.println("Using default port: " + defaultChatPort);
		}

		System.out.println("Using server IP " + serverIP);
		System.out.println("Using server port " + serverPort);

		//IService server = new ProjectServicesProxy(serverIP, serverPort);
		IService server = new ProtoProjectProxy(serverIP,serverPort);

		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("loginView.fxml"));
		Parent root = loader.load();


		LoginController ctrl =
				loader.<LoginController>getController();
		ctrl.setService(server);


		FXMLLoader cloader = new FXMLLoader(
				getClass().getClassLoader().getResource("loggedInView.fxml"));
		Parent croot = cloader.load();


		LoggedInController loggedCtrl = cloader.<LoggedInController>getController();
		loggedCtrl.setServer(server);
		ctrl.setLoggedController(loggedCtrl);
		ctrl.setParent(croot);

		primaryStage.setTitle("MPP project");
		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.show();


	}
}
