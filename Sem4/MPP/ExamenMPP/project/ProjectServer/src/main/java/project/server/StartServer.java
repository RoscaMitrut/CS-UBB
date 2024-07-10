package project.server;

import project.network.utils.AbstractServer;
import project.network.utils.ProjectConcurrentServer;
import project.persistence.hibernate.UserHibernateRepository;
import project.persistence.hibernate.JocHibernateRepository;
import project.persistence.hibernate.ConfiguratiiHibernateRepository;
import project.services.IService;

import java.io.IOException;
import java.util.Properties;
import project.persistence.*;

public class StartServer {
	private static int defaultPort=55555;

	public static void main(String[] args) {
		Properties serverProps = new Properties();
		try{
			serverProps.load(StartServer.class.getResourceAsStream("/projectserver.properties"));
			System.out.println("server props set");
			serverProps.list(System.out);
		}catch (IOException e){
			System.err.println("Cannot find projectserver.properties "+e);
			return;
		}

		UserRepository e1Repo = new UserHibernateRepository();
		JocRepository e2Repo = new JocHibernateRepository();
		ConfiguratiiRepository e3Repo = new ConfiguratiiHibernateRepository();

		IService projectServerImpl = new ProjectServiceImpl(e1Repo,e2Repo,e3Repo);

		int projectServerPort = defaultPort;

		try{
			projectServerPort = Integer.parseInt(serverProps.getProperty("project.server.port"));
		}catch (NumberFormatException nef){
			System.err.println("Wrong  Port Number"+nef.getMessage());
			System.err.println("Using default port "+defaultPort);
		}
		System.out.println("Starting server on port: "+ projectServerPort);

		AbstractServer server = new ProjectConcurrentServer(projectServerPort, projectServerImpl);

		try {
			server.start();
		} catch (Exception e) {
			System.err.println("Error starting the server" + e.getMessage());
		}

	}

}
