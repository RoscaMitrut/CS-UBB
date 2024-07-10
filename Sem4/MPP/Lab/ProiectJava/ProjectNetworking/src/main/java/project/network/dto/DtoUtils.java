package project.network.dto;

import project.model.Bilet;
import project.model.Excursie;
import project.model.User;

import java.time.LocalDateTime;

public class DtoUtils {
	public static User getFromDto(UserDto userdto){
		int id = userdto.getId();
		String pass = userdto.getPassword();
		String username = userdto.getUsername();
		User usr = new User();
		usr.setUsername(username);
		usr.setPassword(pass);
		usr.setId(id);
		return usr;
	}

	public static UserDto getDto(User user){
		int id = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		return new UserDto(id,username,password);
	}

	public static Bilet getFromDto(BiletDto biletdto){
		int id = biletdto.getId();
		int nrPers = biletdto.getNumarPersoane();
		String numeClient = biletdto.getNumeClient();
		int nrTel = biletdto.getNumarTelefonClient();
		int idEx = biletdto.getIdExcursie();
		Bilet bil = new Bilet(nrPers,numeClient,nrTel,idEx);
		bil.setId(id);
		return bil;
	}

	public static BiletDto getDto(Bilet bilet){
		int id = bilet.getId();
		int nrPers = bilet.getNumarPersoane();
		String numeClient = bilet.getNumeClient();
		int nrTel = bilet.getNumarTelefonClient();
		int idEx = bilet.getIdExcursie();
		return new BiletDto(nrPers,numeClient,nrTel,idEx,id);
	}
	public static Excursie getFromDto(ExcursieDto excursiedto){
		String obiectivVizitat = excursiedto.getObiectivVizitat();
		LocalDateTime oraPlecare = excursiedto.getOraPlecare();
		String firmaTransport = excursiedto.getFirmaTransport();
		Double pret = excursiedto.getPret();
		int locuriDisponibile = excursiedto.getLocuriDisponibile();
		int id = excursiedto.getId();
		Excursie exc = new Excursie(obiectivVizitat,oraPlecare,firmaTransport,pret,locuriDisponibile);
		exc.setId(id);
		return exc;
	}

	public static ExcursieDto getDto(Excursie excursie){
		String obiectivVizitat = excursie.getObiectivVizitat();
		LocalDateTime oraPlecare = excursie.getOraPlecare();
		String firmaTransport = excursie.getFirmaTransport();
		Double pret = excursie.getPret();
		int locuriDisponibile = excursie.getLocuriDisponibile();
		int id = excursie.getId();
		return new ExcursieDto(obiectivVizitat,oraPlecare,firmaTransport,pret,locuriDisponibile,id);
	}

	public static Excursie[] getFromDto(ExcursieDto[] excursiidto){
		Excursie[] excursii = new Excursie[excursiidto.length];
		for(int i =0;i< excursiidto.length;i++){
			excursii[i]=getFromDto(excursiidto[i]);
		}
		return excursii;
	}
	public static ExcursieDto[] getDto(Excursie[] excursii){
		ExcursieDto[] excursiiDto = new ExcursieDto[excursii.length];
		for (int i=0;i< excursii.length;i++){
			excursiiDto[i] = getDto(excursii[i]);
		}
		return excursiiDto;
	}



}
