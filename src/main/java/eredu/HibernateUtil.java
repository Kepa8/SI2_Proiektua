package eredu;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil { 
	private static final SessionFactory sessionFactory = buildSessionFactory(); 
	private static SessionFactory buildSessionFactory() {
		try { 
			// SessionFactory klaseko instantzia bat sortzen du, "src" direktorioan  
			// kokatuta behar duen hibernate.cfg.xml fitxategian agertzen diren balioekin 
			// SessionFactory instantzia "src" direktorioan aurkitzen ez badu, erabili hurrengoa: 
			// return new Configuration().configure(new File("src/hibernate.cfg.xml"))
			// .buildSessionFactory();
			// Eta gainera, fitxategiaren izena ("hibernate.cfg.xml") desberdina izan liteke 
					return new Configuration().configure().buildSessionFactory(); 
					} catch (Throwable ex) 
		{ System.err.println("Errorea SessionFactory sortzen." + ex); 
		throw new ExceptionInInitializerError(ex); 
		} }
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
		} 
}
