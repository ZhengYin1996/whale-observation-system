package models;

import com.typesafe.config.Config;
import play.libs.Json;

import java.util.ArrayList;

/**
 * This is the model class in the MVC pattern: it stores all the data which the views access.
 */

public class DataModel {
    private ArrayList<Whale> currentWhales;
    private ArrayList<Whale> allWhales;
    private ArrayList<Observation> allObservations;
    // Data Access Layer database hookup
    private final DataAccess db;

    public DataModel(String uri) {
        this.allWhales = new ArrayList<>();
        this.allObservations = new ArrayList<>();
        this.currentWhales = new ArrayList<>();
        this.db = new DataAccess(uri);
    }

    /**
     * Public functions
     */

    public void insertObservation(Observation newObs) {db.insertObservation(newObs);}
    public void addWhaleToCurrent(Whale whale){
        this.currentWhales.add(whale);
    }
    public void deleteAllObservations() {
        this.db.deleteAll();
    }
    public void clearCurrentWhales() {this.currentWhales = new ArrayList<>();}

    /**
     * Getters
     */
    public String getAllObservationsJson() {
        return arrayListToJson(allObservations);
    }
    public String getCurrentWhalesJson() {
        return arrayListToJson(currentWhales);
    }
    public String getAllWhalesJson() { return arrayListToJson(allWhales);}
    public ArrayList<Whale> getWhales(){
        return currentWhales;
    }

    /**
     * Setters
     */
    public void setAllObservations() {
        this.allObservations = db.getAllObservations();
    }
    public void setAllWhales() {
        this.allWhales = db.getAllWhales();
    }


    /**
     * Utility functions
     */
    private String arrayListToJson(ArrayList<?> arrayList) {
        return Json.stringify(Json.toJson(arrayList));
    }

}