package project.network.jsonprot;

import project.model.Excursie;
import project.model.Bilet;
import project.model.User;
import project.network.dto.BiletDto;
import project.network.dto.DtoUtils;
import project.network.dto.ExcursieDto;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProjectServicesProxy implements IService {
	private String host;
	private int port;
	private IObserver client;
	private BufferedReader input;
	private PrintWriter output;
	private Gson gsonFormatter;
	private Socket connection;
	private BlockingQueue<Response> qresponses;
	private volatile boolean finished;

	public ProjectServicesProxy(String host, int port) {
		this.host = host;
		this.port = port;
		qresponses=new LinkedBlockingQueue<Response>();
	}

	public void login(User user, IObserver client) throws ProjectException {
		initializeConnection();

		Request req = JsonProtocolUtils.createLoginRequest(user);
		sendRequest(req);
		Response response = readResponse();
		if(response.getType() == ResponseType.OK){
			this.client=client;
			return;
		}
		if (response.getType() == ResponseType.ERROR){
			String err = response.getErrorMessage();
			closeConnection();
			throw new ProjectException(err);
		}
	}
	public void logout(User user, IObserver client) throws ProjectException {
		Request req = JsonProtocolUtils.createLogoutRequest(user);
		sendRequest(req);
		Response response = readResponse();
		closeConnection();
		if(response.getType()== ResponseType.ERROR){
			String err = response.getErrorMessage();//data().toString()
			throw new ProjectException(err);
		}
	}
	public Excursie[] getExcursii(IObserver client) throws ProjectException {
		Request req=JsonProtocolUtils.createTripsRequest();
		sendRequest(req);
		Response response = readResponse();
		if(response.getType()==ResponseType.ERROR){
			String err = response.getErrorMessage();
			throw new ProjectException(err);
		}
		ExcursieDto[] excursiidto = response.getExcursii();
		Excursie[] excursii = DtoUtils.getFromDto(excursiidto);
		return excursii;
	}
	public Excursie[] getExcursiiLaLocSiOra(Excursie excursie, Integer oraMin, Integer oraMax,IObserver client) throws ProjectException {
		Request req = JsonProtocolUtils.createFilteredTripsRequest(excursie,oraMin,oraMax);
		sendRequest(req);
		Response response = readResponse();
		if(response.getType() == ResponseType.ERROR){
			String err = response.getErrorMessage();
			throw new ProjectException(err);
		}
		ExcursieDto[] excursiidto = response.getExcursii();
		Excursie[] excursii = DtoUtils.getFromDto(excursiidto);
		return excursii;
	}

	public void rezerva(Bilet bilet,IObserver client) throws ProjectException {
		Request req = JsonProtocolUtils.createBoughtTicketRequest(bilet);
		sendRequest(req);
		Response response = readResponse();
		if(response.getType()==ResponseType.ERROR){
			String err = response.getErrorMessage();
			throw new ProjectException(err);
		}
	}


	private void closeConnection(){
		finished = true;
		try{
			input.close();
			output.close();
			connection.close();
			client=null;
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private void sendRequest(Request request) throws ProjectException{
		String reqLine = gsonFormatter.toJson(request);
		try{
			output.println(reqLine);
			output.flush();
		}catch (Exception e){
			throw new ProjectException("Error sending object "+e);
		}
	}

	private Response readResponse() throws ProjectException{
		Response response=new Response();
		try{
			response = qresponses.take();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return response;
	}

	private void initializeConnection() throws ProjectException{
		try{
			gsonFormatter=new Gson();
			connection = new Socket(host,port);
			output = new PrintWriter(connection.getOutputStream());
			output.flush();
			input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			finished=false;
			startReader();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private void startReader(){
		Thread tw=new Thread(new ReaderThread());
		tw.start();
	}

	private void handleUpdate(Response response) throws ProjectException {
		if (response.getType()== ResponseType.BOUGHT_TICKET){
			System.out.println("Ticket bought");
			try {
				client.ticketBought();
			} catch (ProjectException e) {
				e.printStackTrace();
			}

		}
	}

	private boolean isUpdate(Response response){
		return response.getType()==ResponseType.BOUGHT_TICKET;
	}

	private class ReaderThread implements Runnable{
		public void run(){
			while (!finished){
				try{
					String responseLine = input.readLine();
					System.out.println("response recieved "+responseLine);
					Response response = gsonFormatter.fromJson(responseLine,Response.class);
					if (isUpdate(response)){
						handleUpdate(response);
					}else{

						try {
							qresponses.put(response);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					System.out.println("Reading error "+e);
				}
			}
		}
	}
}
