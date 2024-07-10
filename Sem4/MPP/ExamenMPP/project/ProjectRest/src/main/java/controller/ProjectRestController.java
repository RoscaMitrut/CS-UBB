package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Configuratii;
import project.persistence.*;
import project.model.Joc;

import java.util.ArrayList;

@RestController
@RequestMapping("/project")
@CrossOrigin("*")
public class ProjectRestController {

	private JocRepository e2Repo;
	private ConfiguratiiRepository e3Repo;

	@Autowired
	public void setE2Repository(JocRepository e2Repo){
		this.e2Repo = e2Repo;
	}

	@Autowired
	public void setE3Repository(ConfiguratiiRepository e3Repo){this.e3Repo = e3Repo;}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Iterable<Joc> find(@PathVariable Integer id){
		ArrayList<Joc> jocuri = (ArrayList<Joc>) e2Repo.findAll();
		ArrayList<Joc> jocuri_actual = new ArrayList<>();
		for (Joc joc:jocuri){
			if(joc.getIdJucator()==id){
				jocuri_actual.add(joc);
			}
		}
		return jocuri_actual;
	}

	@RequestMapping(value = "/{in}",method = RequestMethod.POST)
	public Configuratii add(@PathVariable String in){
		Configuratii conf = new Configuratii(in);
		e3Repo.add(conf);
		return conf;
	}

}
