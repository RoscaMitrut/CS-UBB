package project.network.jsonprot;

import com.google.gson.Gson;
import project.model.Bilet;
import project.model.Excursie;
import project.model.User;
import project.network.dto.BiletDto;
import project.network.dto.DtoUtils;
import project.network.dto.ExcursieDto;
import project.network.dto.UserDto;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProjectClientWorker implements Runnable, IObserver {
	private IService server;
	private Socket connection;

	private BufferedReader input;
	private PrintWriter output;
	private Gson gsonFormatter;
	private volatile boolean connected;
	public ProjectClientWorker(IService server, Socket connection) {
		this.server = server;
		this.connection = connection;
		gsonFormatter=new Gson();
		try{
			output=new PrintWriter(connection.getOutputStream());
			input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			connected=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(connected){
			try {
				String requestLine=input.readLine();
				Request request=gsonFormatter.fromJson(requestLine, Request.class);
				Response response=handleRequest(request);
				if (response!=null){
					sendResponse(response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			input.close();
			output.close();
			connection.close();
		} catch (IOException e) {
			System.out.println("Error "+e);
		}
	}
	private static Response okResponse=JsonProtocolUtils.createOkResponse();

	private Response handleRequest(Request request){
	Response response = new Response();
		if (request.getType()== RequestType.LOGIN){
			System.out.println("Login request ..."+request.getType());
			UserDto udto=request.getUser();
			User user= DtoUtils.getFromDto(udto);
			try {
				server.login(user, this);
				return okResponse;
			} catch (ProjectException e) {
				connected=false;
				return JsonProtocolUtils.createErrorResponse(e.getMessage());
			}
		}

		if (request.getType()== RequestType.LOGOUT){
			System.out.println("Logout request");

			UserDto udto=request.getUser();
			User user=DtoUtils.getFromDto(udto);
			try {
				server.logout(user, this);
				connected = false;
				return okResponse;
			} catch (ProjectException e) {
				return JsonProtocolUtils.createErrorResponse(e.getMessage());
			}
		}

		if (request.getType()== RequestType.GET_TRIPS){
			System.out.println("GET_TRIPS request");

			try {
				Excursie[] excursii = server.getExcursii(this);
				ExcursieDto[] exdto = DtoUtils.getDto(excursii);
				response.setExcursii(exdto);
				response.setType(ResponseType.TRIPS);
				return response;
			} catch (ProjectException e) {
				return JsonProtocolUtils.createErrorResponse(e.getMessage());
			}
		}

		if (request.getType()== RequestType.GET_FILTERED_TRIPS){
			System.out.println("GET_FILTERED_TRIPS request");

			ExcursieDto excdto = request.getExcursie();
			Excursie exc = DtoUtils.getFromDto(excdto);
			Integer min = request.getOramin();
			Integer max = request.getOramax();
			try {
				Excursie[] excursii = server.getExcursiiLaLocSiOra(exc,min,max,this);
				ExcursieDto[] exdto = DtoUtils.getDto(excursii);
				response.setExcursii(exdto);
				response.setType(ResponseType.FILTERED_TRIPS);
				return response;
			} catch (ProjectException e) {
				return JsonProtocolUtils.createErrorResponse(e.getMessage());
			}
		}

		if (request.getType()== RequestType.BUY_TICKET){
			System.out.println("BUY_TICKET request");

			BiletDto bildto = request.getBilet();
			Bilet bilet = DtoUtils.getFromDto(bildto);
			try {
				server.rezerva(bilet,this);
				return response;
			} catch (ProjectException e) {
				return JsonProtocolUtils.createErrorResponse(e.getMessage());
			}
		}

		return response;
	}

	private void sendResponse(Response response) throws IOException{
		String responseLine=gsonFormatter.toJson(response);
		System.out.println("sending response "+responseLine);
		synchronized (output) {
			output.println(responseLine);
			output.flush();
		}
	}

	public void ticketBought() {
		//Response response = JsonProtocolUtils.create
		Response resp = JsonProtocolUtils.createBoughtTicketResponse();
		System.out.println("Added bilet");
		try {
			sendResponse(resp);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
