package project.network.protobuffprotocol;

import project.model.Bilet;
import project.model.Excursie;
import project.model.User;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProtoProjectWorker implements Runnable, IObserver {
	private IService server;
	private Socket connection;
	private InputStream input;
	private OutputStream output;
	private volatile boolean connected;

	public ProtoProjectWorker(IService server, Socket connection) {
		this.server = server;
		this.connection = connection;
		try{
			output=connection.getOutputStream();
			input=connection.getInputStream();
			connected=true;
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void run() {
		while (connected){
			try{
				// Object request=input.readObject();
				System.out.println("Waiting requests ");
				ProjectProtobufs.ProjectRequest request=ProjectProtobufs.ProjectRequest.parseDelimitedFrom(input);
				System.out.println("Request received: "+request);
				ProjectProtobufs.ProjectResponse response=handleRequest(request);
				if(response!=null){
					sendResponse(response);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		try{
			input.close();
			output.close();
			connection.close();
		}catch (IOException e){
			System.out.println("Error "+e);
		}
	}



	public void ticketBought() throws ProjectException {

	}

	private ProjectProtobufs.ProjectResponse handleRequest(ProjectProtobufs.ProjectRequest request){
		ProjectProtobufs.ProjectResponse response = null;
		switch (request.getType()){
			case LOGIN:{
				System.out.println("Login request...");
				User user = ProtoUtils.getUser(request);
				try{
					server.login(user,this);
					return ProtoUtils.createOkResponse();
				}catch (ProjectException e){
					connected = false;
					return ProtoUtils.createErrorResponse(e.getMessage());
				}
			}
			case LOGOUT:{
				System.out.println("Logout request...");
				User user = ProtoUtils.getUser(request);
				try{
					server.logout(user,this);
					connected=false;
					return ProtoUtils.createOkResponse();
				}catch (ProjectException e){
					return ProtoUtils.createErrorResponse(e.getMessage());
				}
			}
			case GET_TRIPS:{
				System.out.println("Get_Trips request...");
				Excursie[] excursii;
				try {
					excursii = server.getExcursii(this);
					return ProtoUtils.createTripsResponse(excursii);
				}catch (ProjectException e){
					return ProtoUtils.createErrorResponse(e.getMessage());
				}
			}
			case GET_FILTERED_TRIPS:{
				System.out.println(" request...");
				Excursie excursie = ProtoUtils.getExcursieFiltrare(request);
				int oraMin = ProtoUtils.getOraMin(request);
				int oraMax = ProtoUtils.getOraMax(request);
				Excursie[] excursii;
				try{
					excursii = server.getExcursiiLaLocSiOra(excursie,oraMin,oraMax,this);
					return ProtoUtils.createFilteredTripsResponse(excursii);
				}catch (ProjectException e){
					return ProtoUtils.createErrorResponse(e.getMessage());
				}
			}
			case BUY_TICKET:{
				System.out.println(" request...");
				Bilet bilet = ProtoUtils.getBilet(request);
				try{
					server.rezerva(bilet,this);
					return ProtoUtils.createOkResponse();//PLACEHOLDER
				}
				catch (ProjectException e){
					return ProtoUtils.createErrorResponse(e.getMessage());
				}
			}
		}
		return response;
	}

	private void sendResponse(ProjectProtobufs.ProjectResponse response) throws IOException{
		System.out.println("sending response"+response);
		response.writeDelimitedTo(output);
		//output.writeObject(response);
		output.flush();

	}
}
