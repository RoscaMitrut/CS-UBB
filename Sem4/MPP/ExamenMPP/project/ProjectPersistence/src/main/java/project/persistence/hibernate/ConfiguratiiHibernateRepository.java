package project.persistence.hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import project.model.Configuratii;
import project.persistence.*;
@Repository
public class ConfiguratiiHibernateRepository implements ConfiguratiiRepository {

	@Override
	public void add(Configuratii elem) {
		HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
	}

	@Override
	public void update(Integer integer, Configuratii elem) {

	}

	@Override
	public Iterable<Configuratii> findAll() {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {

			return session.createSelectionQuery("from Configuratii ", Configuratii.class)
					.getResultList();
		}	}
}
