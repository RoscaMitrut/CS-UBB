package project.network.utils;

import project.network.protobuffprotocol.ProtoProjectWorker;
import project.services.IService;

import java.net.Socket;

public class ProjectProtobufConcurrentServer extends AbsConcurrentServer{
	private IService server;

	public ProjectProtobufConcurrentServer(int port, IService server) {
		super(port);
		this.server = server;
		System.out.println("ProjectProtobufConcurrentServer");
	}

	@Override
	protected Thread createWorker(Socket client) {
		ProtoProjectWorker worker = new ProtoProjectWorker(server,client);
		Thread tw=new Thread(worker);
		return tw;
	}
}
