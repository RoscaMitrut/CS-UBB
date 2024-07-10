package project.network.jsonprot;

import project.model.Bilet;
import project.model.Excursie;
import project.model.User;
import project.network.dto.DtoUtils;

public class JsonProtocolUtils {
	// LOGIN, LOGOUT,GET_TRIPS,GET_FILTERED_TRIPS, BUY_TICKET; req types

	// OK, ERROR, TRIPS, FILTERED_TRIPS, BOUGHT_TICKET; resp types


	public static Response createOkResponse(){
		Response resp=new Response();
		resp.setType(ResponseType.OK);
		return resp;
	}

	public static Response createErrorResponse(String errorMessage){
		Response resp=new Response();
		resp.setType(ResponseType.ERROR);
		resp.setErrorMessage(errorMessage);
		return resp;
	}

	public static Response createTripsResponse(Excursie[] excursii){
		Response resp = new Response();
		resp.setType(ResponseType.TRIPS);
		resp.setExcursii(DtoUtils.getDto(excursii));
		return resp;
	}

	public static Response createFilteredTripsResponse(Excursie[] excursii){
		Response resp = new Response();
		resp.setType(ResponseType.FILTERED_TRIPS);
		resp.setExcursii(DtoUtils.getDto(excursii));
		return resp;
	}
	public static Response createBoughtTicketResponse(){
		Response resp = new Response();
		resp.setType(ResponseType.BOUGHT_TICKET);
		return resp;
	}
	public static Request createLoginRequest(User user){
		Request req=new Request();
		req.setType(RequestType.LOGIN);
		req.setUser(DtoUtils.getDto(user));
		return req;
	}

	public static Request createLogoutRequest(User user){
		Request req=new Request();
		req.setType(RequestType.LOGOUT);
		req.setUser(DtoUtils.getDto(user));
		return req;
	}

	public static Request createTripsRequest(){
		Request req = new Request();
		req.setType(RequestType.GET_TRIPS);
		return req;
	}
	public static Request createFilteredTripsRequest(Excursie excursie, Integer oramin, Integer oramax){
		Request req = new Request();
		req.setType(RequestType.GET_FILTERED_TRIPS);
		req.setExcursie(DtoUtils.getDto(excursie));
		req.setOramin(oramin);
		req.setOramax(oramax);
		return req;
	}
	public static Request createBoughtTicketRequest(Bilet bilet){
		Request req = new Request();
		req.setType(RequestType.BUY_TICKET);
		req.setBilet(DtoUtils.getDto(bilet));
		return req;
	}
}
