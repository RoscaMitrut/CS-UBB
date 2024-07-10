package project.network.utils;

import project.model.Joc;
import project.model.User;
import project.network.requests.*;
import project.network.responses.*;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;


import java.awt.*;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class ProjectWorker implements Runnable, IObserver {
	private IService server;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private volatile boolean connected;

	public ProjectWorker(IService server, Socket connection) {
		this.server = server;
		this.connection = connection;
		try{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			connected = true;
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (connected) {
			try {
				Object request = input.readObject();
				Response response = handleRequest((Request) request);
				if (response != null) {
					sendResponse(response);
				}
			} catch (Exception exception) {
				System.err.println("Exception: " + exception);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
				System.err.println("Exception: " + exception);
			}
		}

		try {
			input.close();
			output.close();
			connection.close();
		} catch (IOException e) {
			System.out.println("Error " + e);
		}
	}
	private Response handleRequest(Request request) {

		if (request instanceof LoginRequest loginRequest) {
			System.out.println("Login request ...");
			User user = loginRequest.user();
			try{
				server.login(user, this);
			} catch (ProjectException e) {
				connected = false;
				return new ErrorResponse("Couldn't log in");
			}
			return new OkResponse();
		}

		if(request instanceof LogoutRequest logoutRequest){
			System.out.println("Logout request");
			User user = logoutRequest.user();
			try{
				server.logout(user,this);
				connected =false;
				return new OkResponse();
			}catch (ProjectException e){
				return new ErrorResponse(e.getMessage());
			}
		}

		if(request instanceof GameRequest gameRequest){
			Joc game = server.getGame(gameRequest.player());
			return new GameResponse(game);
		}

		if(request instanceof PointsRequest pointsRequest){
			Integer points = server.getPoints(pointsRequest.player());
			return new PointsResponse(points);
		}
		if(request instanceof SelectRequest selectRequest){
			return new SelectResponse(server.select(selectRequest.player(), selectRequest.input()));
		}
		if(request instanceof RankingRequest rankingRequest){
			Joc[] jocuri = server.getRanking().toArray(new Joc[0]);
			return new RankingResponse(jocuri);
		}

		return null;
	}

	private void sendResponse(Response response) throws IOException {
		System.out.println("Sending response " + response);

		synchronized (output) {
			output.writeObject(response);
			output.flush();
		}
	}

	@Override
	public void action(){
		try {
			sendResponse(new FinishedGame());
		} catch (Exception exception) {
			System.err.println("Exception: " + exception);
		}
	}
}
