import project.network.utils.AbstractServer;
import project.network.utils.ProjectProtobufConcurrentServer;
import project.network.utils.ServerException;
import project.persistence.BiletRepository;
import project.persistence.ExcursieRepository;
import project.persistence.UserRepository;
import project.persistence.hibernate.BiletHibernateRepository;
import project.persistence.repository.BiletDBRepository;
import project.persistence.repository.ExcursieDBRepository;
import project.persistence.repository.UserDBRepository;
import project.server.ProjectServiceImpl;
import project.services.IService;

import java.io.IOException;
import java.util.Properties;


public class StartProtobufServer {
	private static int defaultPort=55555;
	public static void main(String[] args) {
		Properties serverProps = new Properties();
		try{
			serverProps.load(StartProtobufServer.class.getResourceAsStream("/projectserver.properties"));
			System.out.println("server props set");
			serverProps.list(System.out);
		}catch (IOException e){
			System.err.println("Cannot find projectserver.properties "+e);
			return;
		}

		UserRepository userRepo = new UserDBRepository(serverProps);
		ExcursieRepository excursieRepo = new ExcursieDBRepository(serverProps);
		//BiletRepository biletRepo = new BiletDBRepository(serverProps);
		BiletRepository biletRepo = new BiletHibernateRepository();

		IService projectServerImpl = new ProjectServiceImpl(userRepo,excursieRepo,biletRepo);

		int projectServerPort = defaultPort;

		try{
			projectServerPort = Integer.parseInt(serverProps.getProperty("project.server.port"));
		}catch (NumberFormatException nef){
			System.err.println("Wrong  Port Number"+nef.getMessage());
			System.err.println("Using default port "+defaultPort);
		}
		System.out.println("Starting server on port: "+ projectServerPort);

		AbstractServer server = new ProjectProtobufConcurrentServer(projectServerPort, projectServerImpl);

		try {
			server.start();
		} catch (ServerException e) {
			System.err.println("Error starting the server" + e.getMessage());
		}

	}

}

