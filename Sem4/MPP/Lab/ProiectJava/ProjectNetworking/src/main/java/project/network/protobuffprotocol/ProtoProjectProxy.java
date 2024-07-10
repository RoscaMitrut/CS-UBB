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
import java.io.Reader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProjectProxy implements IService {
	private String host;
	private int port;
	private IObserver client;
	private InputStream input;
	private OutputStream output;
	private Socket connection;
	private BlockingQueue<ProjectProtobufs.ProjectResponse> qresponses;
	private volatile boolean finished;
	public ProtoProjectProxy(String host, int port) {
		this.host = host;
		this.port = port;
		qresponses = new LinkedBlockingQueue<>();
	}
	@Override
	public void login(User user, IObserver client) throws ProjectException {
		initializeConnection();
		System.out.println("Login request");
		sendRequest(ProtoUtils.createLoginRequest(user));
		ProjectProtobufs.ProjectResponse response = readResponse();
		if (response.getType()==ProjectProtobufs.ProjectResponse.Type.OK){
			this.client=client;
			return;
		}
		if(response.getType()==ProjectProtobufs.ProjectResponse.Type.ERROR){
			String errorText = ProtoUtils.getError(response);
			closeConnection();
			throw new ProjectException(errorText);
		}
	}
	@Override
	public void logout(User user, IObserver client) throws ProjectException {
		sendRequest(ProtoUtils.createLogoutRequest(user));
		ProjectProtobufs.ProjectResponse response = readResponse();
		closeConnection();
		if(response.getType()==ProjectProtobufs.ProjectResponse.Type.ERROR){
			String errorText = ProtoUtils.getError(response);
			throw new ProjectException(errorText);
		}
	}
	@Override
	public Excursie[] getExcursii(IObserver client) throws ProjectException {
		sendRequest(ProtoUtils.createGetTripsRequest());
		ProjectProtobufs.ProjectResponse response=readResponse();
		if(response.getType()==ProjectProtobufs.ProjectResponse.Type.ERROR){
			String errorText = ProtoUtils.getError(response);
			throw new ProjectException(errorText);
		}
		Excursie[] excursii = ProtoUtils.getExcursii(response);
		return excursii;
	}
	@Override
	public Excursie[] getExcursiiLaLocSiOra(Excursie excursie, Integer oraMin, Integer oraMax, IObserver client) throws ProjectException {
		sendRequest(ProtoUtils.createGetFilteredTripsRequest(excursie,oraMin,oraMax));
		ProjectProtobufs.ProjectResponse response = readResponse();
		if(response.getType()==ProjectProtobufs.ProjectResponse.Type.ERROR){
			String errorText = ProtoUtils.getError(response);
			throw new ProjectException(errorText);
		}
		Excursie[] excursii = ProtoUtils.getExcursii(response);
		return excursii;
	}
	@Override
	public void rezerva(Bilet bliet, IObserver client) throws ProjectException {
		sendRequest(ProtoUtils.createBuyTicketRequest(bliet));
		ProjectProtobufs.ProjectResponse response = readResponse();
		if(response.getType()==ProjectProtobufs.ProjectResponse.Type.ERROR){
			String errorText = ProtoUtils.getError(response);
			throw new ProjectException(errorText);
		}
	}

	private void closeConnection(){
		finished=true;
		try{
			input.close();
			output.close();
			connection.close();
			client=null;
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	private void sendRequest(ProjectProtobufs.ProjectRequest request)throws ProjectException{
		try{
			System.out.println("Sending Request "+request);
			//request.writeTo(output);
			request.writeDelimitedTo(output);
			output.flush();
			System.out.println("Request sent");
		}catch (IOException e){
			throw new ProjectException("Error sending object "+e);
		}
	}

	private ProjectProtobufs.ProjectResponse readResponse() throws ProjectException{
		ProjectProtobufs.ProjectResponse response=null;
		try{
			response = qresponses.take();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return response;
	}

	private void initializeConnection() throws ProjectException{
		try{
			connection = new Socket(host,port);
			output = connection.getOutputStream();
			//output.flush();
			input = connection.getInputStream();
			finished=false;
			startReader();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	private void startReader(){
		Thread tw = new Thread(new ReaderThread());
		tw.start();
	}

	private void handleUpdate(ProjectProtobufs.ProjectResponse updateResponse){
		switch (updateResponse.getType()){
			case BOUGHT_TICKET :{
				System.out.println("Ticket bought");
				try{
					client.ticketBought();
				}catch (ProjectException e){
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private class ReaderThread implements Runnable{
		@Override
		public void run(){
			while (!finished){
				try{
					ProjectProtobufs.ProjectResponse response = ProjectProtobufs.ProjectResponse.parseDelimitedFrom(input);
					System.out.println("response recived "+response);

					if(isUpdateResponse(response.getType())){
						handleUpdate(response);
					}else {
						try{
							qresponses.put(response);
						}catch (InterruptedException e){
							e.printStackTrace();
						}
					}
				}catch (IOException e){
					System.out.println("Reading error "+e);
				}
			}
		}
	}


	private boolean isUpdateResponse(ProjectProtobufs.ProjectResponse.Type type){
		switch (type){
			case BOUGHT_TICKET: return true;
		}
		return false;
	}
}
