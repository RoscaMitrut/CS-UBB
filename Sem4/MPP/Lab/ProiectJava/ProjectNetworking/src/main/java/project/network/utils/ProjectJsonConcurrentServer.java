package project.network.utils;

import project.network.jsonprot.ProjectClientWorker;
import project.services.IService;

import java.net.Socket;

public class ProjectJsonConcurrentServer extends AbsConcurrentServer{
	private IService projectServer;
	public ProjectJsonConcurrentServer(int port, IService projectServer) {
		super(port);
		this.projectServer = projectServer;
		System.out.println("Project- ProjectJsonConcurrentServer");
	}

	@Override
	protected Thread createWorker(Socket client) {
		ProjectClientWorker worker=new ProjectClientWorker(projectServer, client);

		Thread tw=new Thread(worker);
		return tw;
	}
}
