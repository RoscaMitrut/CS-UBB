package project.network.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public abstract class AbstractServer {
	private int port;
	private ServerSocket server=null;
	public AbstractServer( int port){
		this.port=port;
	}

	public void start(){
		try{
			server=new ServerSocket(port);
			while(true){
				System.out.println("Waiting for clients ...");
				Socket client=server.accept();
				System.out.println("Client connected ...");
				processRequest(client);
			}
		} catch (Exception e) {
			System.out.println("error");
		}finally {
			stop();
		}
	}

	protected abstract  void processRequest(Socket client);
	public void stop(){
		try {
			server.close();
		} catch (Exception e) {
			System.err.println("error");
		}
	}
}
