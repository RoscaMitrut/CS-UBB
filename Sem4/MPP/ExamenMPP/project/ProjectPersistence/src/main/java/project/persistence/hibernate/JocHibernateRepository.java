package project.persistence.hibernate;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import project.model.Joc;
import project.persistence.JocRepository;

@Repository
public class JocHibernateRepository implements JocRepository {


	@Override
	public void add(Joc elem) {
		HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
	}
	@Override
	public void update(Integer integer, Joc elem) {
	}
	@Override
	public Iterable<Joc> findAll() {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {

			return session.createSelectionQuery("from Joc", Joc.class)
					.getResultList();
		}
	}
}
