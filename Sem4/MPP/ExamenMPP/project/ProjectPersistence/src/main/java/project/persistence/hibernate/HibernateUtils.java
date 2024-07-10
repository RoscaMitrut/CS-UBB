package project.persistence.hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import project.model.*;

public class HibernateUtils {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory(){
		if ((sessionFactory==null)||(sessionFactory.isClosed()))
			sessionFactory=createNewSessionFactory();
		return sessionFactory;
	}

	private static  SessionFactory createNewSessionFactory(){
		sessionFactory = new Configuration()
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Joc.class)
				.addAnnotatedClass(Configuratii.class)
				.buildSessionFactory();
		return sessionFactory;
	}

	public static  void closeSessionFactory(){
		if (sessionFactory!=null)
			sessionFactory.close();
	}
}
