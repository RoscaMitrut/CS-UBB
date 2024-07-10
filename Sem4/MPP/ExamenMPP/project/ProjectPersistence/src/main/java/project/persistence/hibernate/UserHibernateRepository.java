package project.persistence.hibernate;

import org.hibernate.Session;
import project.model.User;
import project.persistence.UserRepository;
import java.util.Optional;


public class UserHibernateRepository implements UserRepository {
	@Override
	public void add(User elem) {
	//
	}

	@Override
	public void update(Integer integer, User elem) {
	//
	}

	@Override
	public Iterable<User> findAll() {
		//
		return null;
	}
@Override
	public Optional<User> findOne(String name){
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {

			var result = session.createSelectionQuery("from User where alias=:usr ", User.class)
					.setParameter("usr", name)
					.getSingleResultOrNull();
					//.getSingleResult();
					//.getResultList().stream().findFirst();
			//System.out.println(result.get().getAlias());
			//System.out.println(result.get().getId());
			//return result;
			return (result != null ? Optional.of(result): Optional.empty());
		}
	}
}
