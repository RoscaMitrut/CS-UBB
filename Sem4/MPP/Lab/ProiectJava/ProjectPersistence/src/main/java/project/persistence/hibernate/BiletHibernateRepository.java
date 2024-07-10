package project.persistence.hibernate;

import org.hibernate.Session;
import project.model.Bilet;
import project.persistence.BiletRepository;

import java.util.Objects;

public class BiletHibernateRepository implements BiletRepository {

	@Override
	public void add(Bilet elem) {
		HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
	}

	@Override
	public void update(Integer id, Bilet elem) {
		HibernateUtils.getSessionFactory().inTransaction(session -> {
			if (!Objects.isNull(session.find(Bilet.class, id))) {
				System.out.println("In update, am gasit mesajul cu id-ul "+id);
				session.merge(elem);
				session.flush();
			}
		});
	}

	@Override
	public Iterable<Bilet> findAll() {
		try(Session session=HibernateUtils.getSessionFactory().openSession()){
			return session.createQuery("from Bilet ",Bilet.class).getResultList();
		}
	}
}
