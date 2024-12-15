package eredua.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import dataAccess.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Ride;

public class QRBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private HibernateDataAccess da= new HibernateDataAccess();
    private String departCity;
    private String arrivalCity;
    private Date data;
    private List<Ride> results;
    private List<String> departCities;
    private List<String> destinationCities;
    

    public void init() {
        loadDepartCities();
        if (departCities == null) {
            departCities = new ArrayList<>();
        }
        if (destinationCities == null) {
            destinationCities = new ArrayList<>();
        }
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
        loadDestinationCities();
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Ride> getResults() {
        return results;
    }

    public void setResults(List<Ride> results) {
        this.results = results;
    }
    
    public List<String> getDepartCities() {
    	loadDepartCities();
        return departCities;
    }

    public List<String> getDestinationCities() {
    	loadDestinationCities();
        return destinationCities;
    }

    public void setDepartCities(List<String> departCities) {
        this.departCities = departCities;
    }

    public void setDestinationCities(List<String> arrivalCities) {
        this.destinationCities = arrivalCities;
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fecha seleccionada: " + event.getObject()));
    }

    public void loadDepartCities() {
    	
        try {
        List<String> cities = da.getDepartCities();
        if (cities != null) {
            this.departCities = cities;
        } else {
            this.departCities = new ArrayList<>();
        }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading departure cities", e.getMessage()));
            this.departCities = new ArrayList<>();
        }
        

    }

    public void loadDestinationCities() {
    	try {
            if (departCity != null && !departCity.isEmpty()) {
                destinationCities = da.getArrivalCities(departCity);
                System.out.println("Ciudades de destino: " + destinationCities);
            } else {
                destinationCities = new ArrayList<>();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading destination cities", e.getMessage()));
            destinationCities = new ArrayList<>();
        }
    }
    	
    	public void getRides() {
    		try {
                // Asegúrate de que el método getRides de HibernateDataAccess esté funcionando correctamente
                this.results = da.getRides(departCity, arrivalCity, data);

                // Si no hay resultados, mostrar un mensaje
                if (results == null || results.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(
                        null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron viajes.", "")
                    );
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(
                    null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener los viajes: " + e.getMessage(), "")
                );
            }	
    }
    	
    	
}