package project.network.protobuffprotocol;

import project.model.Bilet;
import project.model.Excursie;
import project.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ProtoUtils {

	public static DateTimeFormatter format = new DateTimeFormatterBuilder()
			.appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a"))
			.appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
			.toFormatter();

	public static ProjectProtobufs.ProjectRequest createLoginRequest(User user){
		ProjectProtobufs.User userDTO=ProjectProtobufs.User.newBuilder().setId(user.getId().toString()).setPassword(user.getPassword()).setUsername(user.getUsername()).build();

		ProjectProtobufs.ProjectRequest request = ProjectProtobufs.ProjectRequest.newBuilder().setType(ProjectProtobufs.ProjectRequest.Type.LOGIN)
				.setUser(userDTO).build();

		return ProjectProtobufs.ProjectRequest.newBuilder()
				.setType(ProjectProtobufs.ProjectRequest.Type.LOGIN)
				.setUser(userDTO)
				.build();
	}

	public static ProjectProtobufs.ProjectRequest createLogoutRequest(User user){
		ProjectProtobufs.User userDTO=ProjectProtobufs.User.newBuilder().setId(user.getId().toString()).setPassword(user.getPassword()).setUsername(user.getUsername()).build();
		ProjectProtobufs.ProjectRequest request = ProjectProtobufs.ProjectRequest.newBuilder().setType(ProjectProtobufs.ProjectRequest.Type.LOGOUT)
				.setUser(userDTO).build();

		return request;
	}

	public static ProjectProtobufs.ProjectRequest createGetTripsRequest(){
		ProjectProtobufs.ProjectRequest request = ProjectProtobufs.ProjectRequest.newBuilder()
				.setType(ProjectProtobufs.ProjectRequest.Type.GET_TRIPS)
				.build();

		return request;
	}

	public static ProjectProtobufs.ProjectRequest createGetFilteredTripsRequest(Excursie excursie, Integer oramin,Integer oramax){
		ProjectProtobufs.Excursie excursieDTO = ProjectProtobufs.Excursie.newBuilder()
				//.setId(excursie.getId().toString())
				//.setFirmaTransport(excursie.getFirmaTransport())
				//.setLocuriDisponibile(String.valueOf(excursie.getLocuriDisponibile()))
				.setObiectivVizitat(excursie.getObiectivVizitat())
				.setOraPlecare(excursie.getOraPlecare().toString())
				//.setPret(excursie.getPret().toString())
				.build();
		ProjectProtobufs.ProjectRequest request = ProjectProtobufs.ProjectRequest.newBuilder()
				.setType(ProjectProtobufs.ProjectRequest.Type.GET_FILTERED_TRIPS)
				.setExcursie(excursieDTO)
				.setOramin(oramin.toString())
				.setOramax(oramax.toString())
				.build();
		return request;
	}

	public static ProjectProtobufs.ProjectRequest createBuyTicketRequest(Bilet bilet){
		ProjectProtobufs.Bilet biletDTO = ProjectProtobufs.Bilet.newBuilder()
				.setId(bilet.getId().toString())
				.setNumarPersoane(String.valueOf(bilet.getNumarPersoane()))
				.setNumarTelefonClient(String.valueOf(bilet.getNumarTelefonClient()))
				.setNumeClient(bilet.getNumeClient())
				.setIdExcursie(String.valueOf(bilet.getIdExcursie()))
				.build();

		ProjectProtobufs.ProjectRequest request = ProjectProtobufs.ProjectRequest.newBuilder()
				.setType(ProjectProtobufs.ProjectRequest.Type.BUY_TICKET)
				.setBilet(biletDTO)
				.build();

		return request;
	}

	public static ProjectProtobufs.ProjectResponse createOkResponse(){
		ProjectProtobufs.ProjectResponse response = ProjectProtobufs.ProjectResponse.newBuilder()
				.setType(ProjectProtobufs.ProjectResponse.Type.OK).build();

		return response;
	}

	public static ProjectProtobufs.ProjectResponse createErrorResponse(String text){
		ProjectProtobufs.ProjectResponse response = ProjectProtobufs.ProjectResponse.newBuilder()
				.setType(ProjectProtobufs.ProjectResponse.Type.ERROR)
				.setErrorMessage(text)
				.build();
		return response;
	}

	public static ProjectProtobufs.ProjectResponse createTripsResponse(Excursie[] excursii){
		ProjectProtobufs.ProjectResponse.Builder response = ProjectProtobufs.ProjectResponse.newBuilder()
				.setType(ProjectProtobufs.ProjectResponse.Type.TRIPS);
		for (Excursie excursie: excursii){
			ProjectProtobufs.Excursie excursieDTO = ProjectProtobufs.Excursie.newBuilder()
					.setId(excursie.getId().toString())
					.setFirmaTransport(excursie.getFirmaTransport())
					.setLocuriDisponibile(String.valueOf(excursie.getLocuriDisponibile()))
					.setObiectivVizitat(excursie.getObiectivVizitat())
					.setOraPlecare(excursie.getOraPlecare().toString())
					.setPret(excursie.getPret().toString())
					.build();
			response.addExcursii(excursieDTO);
		}

		return response.build();
	}

	public static ProjectProtobufs.ProjectResponse createFilteredTripsResponse(Excursie[] excursii){
		ProjectProtobufs.ProjectResponse.Builder response = ProjectProtobufs.ProjectResponse.newBuilder()
				.setType(ProjectProtobufs.ProjectResponse.Type.FILTERED_TRIPS);
		for (Excursie excursie: excursii){
			ProjectProtobufs.Excursie excursieDTO = ProjectProtobufs.Excursie.newBuilder()
					.setId(excursie.getId().toString())
					.setFirmaTransport(excursie.getFirmaTransport())
					.setLocuriDisponibile(String.valueOf(excursie.getLocuriDisponibile()))
					.setObiectivVizitat(excursie.getObiectivVizitat())
					.setOraPlecare(excursie.getOraPlecare().toString())
					.setPret(excursie.getPret().toString())
					.build();
			response.addExcursii(excursieDTO);
		}

		return response.build();
	}

	public static ProjectProtobufs.ProjectResponse createBuyTicketResponse(){
		ProjectProtobufs.ProjectResponse response = ProjectProtobufs.ProjectResponse.newBuilder()
				.setType(ProjectProtobufs.ProjectResponse.Type.BOUGHT_TICKET).build();
		return response;
	}

	public static String getError(ProjectProtobufs.ProjectResponse response){
		String errorMsg = response.getErrorMessage();
		return errorMsg;
	}

	//USELESS? PLACEHOLDER
	public static User getUser(ProjectProtobufs.ProjectResponse response){
		User user = new User();
		user.setId(Integer.valueOf(response.getUser().getId()));
		return user;
	}
	//USELESS? PLACEHOLDER
	public static Bilet getBilet(ProjectProtobufs.ProjectResponse response){
		int numarPersoane = Integer.parseInt(response.getBilet().getNumarPersoane());
		String numeClient = response.getBilet().getNumeClient();
		int numarTelefonClient = Integer.parseInt(response.getBilet().getNumarTelefonClient());
		int idExcursie = Integer.parseInt(response.getBilet().getIdExcursie());
		int id = Integer.parseInt(response.getBilet().getId());
		Bilet bilet = new Bilet(numarPersoane,numeClient,numarTelefonClient,idExcursie);
		bilet.setId(id);
		return bilet;
	}

	public static Excursie[] getExcursii(ProjectProtobufs.ProjectResponse response){
		Excursie[] excursii = new Excursie[response.getExcursiiCount()];

		for(int i=0;i<response.getExcursiiCount();i++){
			ProjectProtobufs.Excursie excursieDTO = response.getExcursii(i);
			Excursie excursie = new Excursie();
			excursie.setId(Integer.valueOf(excursieDTO.getId()));
			excursie.setPret(Double.valueOf(excursieDTO.getPret()));
			String ora  = excursieDTO.getOraPlecare();
			excursie.setOraPlecare(LocalDateTime.parse(ora,format));
			excursie.setObiectivVizitat(excursieDTO.getObiectivVizitat());
			excursie.setFirmaTransport(excursieDTO.getFirmaTransport());
			excursie.setLocuriDisponibile(Integer.parseInt(excursieDTO.getLocuriDisponibile()));
			excursii[i]=excursie;
		}

		return excursii;
	}

	public static User getUser(ProjectProtobufs.ProjectRequest request){
		User user = new User();
		user.setUsername(request.getUser().getUsername());
		user.setPassword(request.getUser().getPassword());
		user.setId(Integer.valueOf(request.getUser().getId()));
		return user;
	}

	public static Excursie getExcursie(ProjectProtobufs.ProjectRequest request){
		Excursie excursie = new Excursie();
		excursie.setId(Integer.valueOf(request.getExcursie().getId()));
		excursie.setPret(Double.valueOf(request.getExcursie().getPret()));
		excursie.setLocuriDisponibile(Integer.valueOf(request.getExcursie().getLocuriDisponibile()));
		String ora  = request.getExcursie().getOraPlecare();
		excursie.setOraPlecare(LocalDateTime.parse(ora,format));
		excursie.setFirmaTransport(request.getExcursie().getFirmaTransport());
		excursie.setObiectivVizitat(request.getExcursie().getObiectivVizitat());
		return excursie;
	}

	public static Excursie getExcursieFiltrare(ProjectProtobufs.ProjectRequest request){
		Excursie excursie = new Excursie();
		excursie.setObiectivVizitat(request.getExcursie().getObiectivVizitat());
		excursie.setOraPlecare(LocalDateTime.parse(request.getExcursie().getOraPlecare()));
		return excursie;
	}

	public static Integer getOraMin(ProjectProtobufs.ProjectRequest request){
		return Integer.valueOf(request.getOramin());
	}

	public static Integer getOraMax(ProjectProtobufs.ProjectRequest request){
		return Integer.valueOf(request.getOramax());
	}
	public static Bilet getBilet(ProjectProtobufs.ProjectRequest request){
		Bilet bilet = new Bilet();
		bilet.setId(Integer.valueOf(request.getBilet().getId()));
		bilet.setIdExcursie(Integer.parseInt(request.getBilet().getIdExcursie()));
		bilet.setNumarPersoane(Integer.parseInt(request.getBilet().getNumarPersoane()));
		bilet.setNumeClient(request.getBilet().getNumeClient());
		bilet.setNumarTelefonClient(Integer.parseInt(request.getBilet().getNumarTelefonClient()));
		return bilet;
	}
}
