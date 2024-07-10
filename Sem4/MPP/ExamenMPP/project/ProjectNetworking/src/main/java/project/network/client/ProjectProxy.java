package project.network.client;

import project.model.Joc;
import project.model.User;
import project.network.requests.*;
import project.network.responses.*;
import project.services.GameStatus;
import project.services.IObserver;
import project.services.IService;
import project.services.ProjectException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProjectProxy implements IService {
	private String host;
	private int port;
	private IObserver client;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket connection;

	public ProjectProxy(String host, int port) {
		this.host = host;
		this.port = port;
		responses = new LinkedBlockingQueue<>();
	}

	private BlockingQueue<Response> responses;
	private volatile boolean finished;

	private void initializeConnection(){
		try{
			connection = new Socket(host,port);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
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


	@Override
	public void login(User user, IObserver client) throws ProjectException {
		initializeConnection();

		sendRequest(new LoginRequest(user));
		Response response = readResponse();

		if(response instanceof OkResponse){
			this.client = client;
			return;
		}
		if(response instanceof ErrorResponse error){
			System.out.println(error);
			closeConnection();
			throw new ProjectException("Auth error");
		}
		throw new ProjectException("Auth error");
	}

	@Override
	public void logout(User user, IObserver client) throws ProjectException {
		sendRequest(new LogoutRequest(user));
		Response response = readResponse();
		closeConnection();
		if(response instanceof ErrorResponse error){
			System.out.println(error);
			throw new ProjectException(error.message());
		}
	}

	@Override
	public GameStatus select(User user,String input) {
		sendRequest(new SelectRequest(user,input));
		Response response = readResponse();

		if(response instanceof SelectResponse status){
			return status.status();
		}
		if(response instanceof ErrorResponse error){
			System.out.println(error);
		}
		return GameStatus.LOSE;
	}

	@Override
	public Joc getGame(User user) {
		sendRequest(new GameRequest(user));
		Response response = readResponse();

		if (response instanceof ErrorResponse error) {
			System.out.println(error);
			return null;
		}

		return ((GameResponse) response).game();	}

	@Override
	public Integer getPoints(User user) {
		sendRequest(new PointsRequest(user));
		Response response = readResponse();

		if (response instanceof ErrorResponse error) {
			System.out.println(error);
			return null;
		}

		return ((PointsResponse) response).points();	}

	@Override
	public List<Joc> getRanking() {
		sendRequest(new RankingRequest());
		Response response = readResponse();

		if(response instanceof ErrorResponse errorResponse){
			System.out.println(errorResponse);
			return new ArrayList<>();
		}

		return Arrays.asList(((RankingResponse) response).games());
	}


	private void handleUpdate(UpdateResponse update){
		if (update instanceof FinishedGame) {
			System.out.println("updated !");
			try {
				client.action();
			} catch (Exception exception) {
				System.err.println("Exception: " + exception);
			}
		}
	}
	private void sendRequest(Request request){
		try{
			output.writeObject(request);
			output.flush();
		}catch (Exception e){
			System.err.println("Exception" + e);
		}
	}

	private Response readResponse(){
		Response response = null;
		try{
			response = responses.take();
		}catch (Exception e){
			System.err.println("Exception" + e);
		}
		return response;
	}

	private class ReaderThread implements Runnable{
		@Override
		public void run(){
			while (!finished){
				try{
					Object response = input.readObject();
					System.out.println("response recived "+response);

					if(response instanceof UpdateResponse){
						handleUpdate((UpdateResponse) response);
					}else {
						try{
							responses.put((Response) response);
						}catch (Exception e){
							System.err.println("Exception "+e);
						}
					}
				}catch (Exception e){
					System.out.println("Reading error "+e);
				}
			}
		}
	}
}
