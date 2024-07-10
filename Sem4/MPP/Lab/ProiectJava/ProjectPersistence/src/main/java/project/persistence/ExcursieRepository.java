package project.persistence;


import project.model.Excursie;

import java.time.LocalDateTime;

public interface ExcursieRepository extends Repository<Integer, Excursie>{
	Iterable<Excursie> findExcursiiLaLocSiOra(String obiectivVizitat, LocalDateTime oraMin,LocalDateTime oraMax);
	void updateLocuriDisponibile(int id,int numarLocuriDorite);

	int checkLocuriDisponibile(int id);

	void delete(Integer id);
	}
