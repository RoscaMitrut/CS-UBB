package project.persistence;
import org.hibernate.validator.internal.metadata.PredefinedScopeBeanMetaDataManager;
import project.model.User;
import project.persistence.hibernate.HibernateUtils;
import org.hibernate.Session;
import java.util.Optional;

public class temporar {
	public static void main(String[] args) {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			User result = session.createSelectionQuery("from User where alias=:usr ", User.class)
					.setParameter("usr", "user2")
					.getSingleResult();
			System.out.println(result.getId());
		}
	}
}
