package project.network.utils;

import project.services.IService;

import java.net.Socket;

public class ProjectConcurrentServer extends AbsConcurrentServer{
	private IService server;

	public ProjectConcurrentServer(int port, IService server) {
		super(port);
		this.server = server;
		System.out.println("ProjectConcurrentServer");
	}
	@Override
	protected Thread createWorker(Socket client){
		ProjectWorker worker = new ProjectWorker(server,client);
		Thread tw = new Thread(worker);
		return tw;
	}
}
