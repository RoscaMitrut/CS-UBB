package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Excursie;
import project.persistence.ExcursieRepository;
import project.persistence.RepositoryException;
import project.persistence.repository.ExcursieDBRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("project/excursii")
@CrossOrigin("*")
public class ProjectExcursiiRestController {

	@Autowired
	private ExcursieRepository excursieRepo;

	@GetMapping("/hello")
	public String greeting() {
		System.out.println("Greeting");
		return "Hello, World!";
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Excursie> findAll() {
		System.out.println("Get all excursii");
		return excursieRepo.findAll();
	}
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		System.out.println("FindOne excursie");
		ArrayList<Excursie> excursii = (ArrayList<Excursie>) excursieRepo.findAll();
		for (Excursie elem : excursii){
			if(elem.getId().equals(id)){
				return new ResponseEntity<Excursie>(elem,HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
	}
	@RequestMapping(method = RequestMethod.POST)
	public Excursie add(@RequestBody Excursie excursie) {
		System.out.println("Add excursie");
		excursieRepo.add(excursie);
		return excursie;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Excursie update(@PathVariable String id,@RequestBody Excursie excursie) {
		System.out.println("Update excursie");
		excursieRepo.update(Integer.valueOf(id), excursie);
		return excursie;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id){
		System.out.println("Delete excursie");
		excursieRepo.delete(id);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}

	//@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	//public Integer checkLocuriDisponibile(@RequestBody Integer id) {
	//	return excursieRepo.checkLocuriDisponibile(id);
	//}

	//@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	//public void updateLocuriDisponibile(@RequestBody Integer id, Integer locuri) {
	//	System.out.println("UPDATE LOCURI DISPONIBILE");
	//	excursieRepo.updateLocuriDisponibile(id, locuri);
	//}

	//@RequestMapping(method = RequestMethod.GET)
	//public Iterable<Excursie> findExcursiiLaLocSiOra(@RequestBody String obiectiv, LocalDateTime oraMin, LocalDateTime oraMax) {
	//	return excursieRepo.findExcursiiLaLocSiOra(obiectiv, oraMin, oraMax);
	//}

	@ExceptionHandler(RepositoryException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String excursieError(RepositoryException e){
		return e.getMessage();
	}
}