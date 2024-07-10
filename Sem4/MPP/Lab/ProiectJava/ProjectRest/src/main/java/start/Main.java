package start;

import client.RestTest;
import controller.ServiceException;
import project.model.Excursie;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		RestTest restTest = new RestTest();

		try {
			System.out.println(Arrays.toString(restTest.getAll()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		try {
			System.out.println(restTest.getById("2"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}


		try {
			System.out.println(restTest.create(new Excursie("TEMP OBIECTIV", LocalDateTime.now(),"TEMP FIRMA",111.111,111)));
		} catch (ServiceException e) {
			e.printStackTrace();
		}


		try {
			Excursie flight = new Excursie("BLABLABLA", LocalDateTime.now(),"TEMP FIRMA",111.111,111);
			restTest.update(12,flight);

			System.out.println(Arrays.toString(restTest.getAll()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			restTest.delete("12");
			System.out.println(Arrays.toString(restTest.getAll()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}
}