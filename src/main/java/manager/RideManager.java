package manager;

import domain.Driver;
import domain.Ride;
import eredu.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dataAccess.HibernateDataAccess;

public class RideManager {

    private HibernateDataAccess dataAccess;

    public RideManager() {
        dataAccess = new HibernateDataAccess();
    }

    /**
     * Crea y almacena un viaje en la base de datos.
     *
     * @param from     Origen del viaje
     * @param to       Destino del viaje
     * @param date     Fecha del viaje
     * @param nPlaces  Número de plazas disponibles
     * @param price    Precio del viaje
     * @param active   Estado del viaje (activo/inactivo)
     */
    private void createAndStoreRide(String from, String to, Date date, int nPlaces, float price, Driver driver) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
        	tx = session.beginTransaction();
			
            
			if (driver.getEmail() != null) {
	            session.saveOrUpdate(driver);  // Asegúrate de que el driver se guarda en la base de datos
	        }
			Ride ride = new Ride(from, to, date, nPlaces, price, driver);
			session.saveOrUpdate(ride);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Recupera todos los viajes almacenados en la base de datos.
     *
     * @return Lista de objetos Ride
     */
    private List<Ride> getAllRides() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Ride> rides = null;
        try {
            tx = session.beginTransaction();

            // Ejecutar la consulta para obtener todos los viajes
            rides = session.createQuery("from Ride").list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return rides;
    }

    public static void main(String[] args) {
        RideManager manager = new RideManager();
        Driver d= new Driver("driver8@gmail.com", "dr");
        
        HibernateDataAccess a = new HibernateDataAccess();
        a.initializeDB();
        // Crear un viaje de ejemplo
        manager.createAndStoreRide("Donosti", "Bilbo", new Date(), 3, 15.5f, d);
        manager.createAndStoreRide("a", "b", new Date(), 2, 40f, d);
        manager.createAndStoreRide("c", "d", new Date(), 4, 35f, d);
        manager.createAndStoreRide("e", "f", new Date(), 3, 30f, d);
        // Obtener y mostrar todos los viajes
        List<Ride> rides = manager.getAllRides();
        if (rides != null) {
            for (Ride ride : rides) {
                System.out.println(ride);
            }
        }
    }
}