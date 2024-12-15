package eredua.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import domain.Driver;

import businessLogic.BLFacade;
import dataAccess.HibernateDataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CRBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date data;
	private String departCity;
	private String arrivalCity;
	private Integer seats;
	private float price;
	private String driver;

	 private HibernateDataAccess hibernateDataAccess = new HibernateDataAccess();
	
	public CRBean() {
		//
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	 public Ride createRide() {
		 String email= "driver1@gmail.com";
	        try {
	            // HibernateDataAccess-en metodoa erabiltzen da Ride-a sortzeko
	            Ride ride = hibernateDataAccess.createRide(departCity, arrivalCity, data, seats, price, email);

	            // Mezu arrakastatsua gehitu
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Ride-a ondo sortu da", null));

	            return ride;

	        } catch (RideMustBeLaterThanTodayException e) {
	            // Data okerra bada, errorea bistaratzen da
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: " + e.getMessage(), null));
	        } catch (RideAlreadyExistException e) {
	            // Ride-a existitzen bada, errorea bistaratzen da
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: " + e.getMessage(), null));
	        } catch (Exception e) {
	            // Beste errore bat gertatzen bada
	            e.printStackTrace();
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea Ride-a sortzerakoan: " + e.getMessage(),
	                            null));
	        }
	        return null;
	    }
}