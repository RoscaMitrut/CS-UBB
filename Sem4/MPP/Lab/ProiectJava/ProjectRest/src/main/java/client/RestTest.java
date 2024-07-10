package client;

import controller.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import project.model.Excursie;

import java.util.concurrent.Callable;

public class RestTest {
	public static final String URL = "http://localhost:8080/project/excursii";
	private RestTemplate restTemplate = new RestTemplate();
	private <T> T execute(Callable<T> callable) {
		try {
			return callable.call();
		} catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Excursie[] getAll() {
		return execute(() -> restTemplate.getForObject(URL, Excursie[].class));
	}

	public Excursie getById(String id) {
		return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Excursie.class));
	}

	public Excursie create(Excursie excursie) {
		return execute(() -> restTemplate.postForObject(URL, excursie, Excursie.class));
	}

	public void update(Integer id,Excursie excursie) {
		execute(() -> {
			restTemplate.put(String.format("%s/%s", URL, id), excursie);
			return null;
		});
	}

	public void delete(String id) {
		execute(() -> {
			restTemplate.delete(String.format("%s/%s", URL, id));
			return null;
		});
	}
}
