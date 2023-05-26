package models;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;

import play.data.validation.Constraints;

/**
 * A basic Observation class
 */

public class Observation {

    private ArrayList<Whale> whales;

    private int id = -1;

    @Constraints.Min(-180)
    @Constraints.Max(180)
    private double longitude;

    @Constraints.Min(-90)
    @Constraints.Max(90)
    private double latitude;

    @Constraints.Required
    private Date date;

    public Observation(){

    }

    public Observation(double longitude, double latitude, Date date, ArrayList<Whale> whales){
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.whales = whales;
    }

   public Observation(double longitude, double latitude, Date date){
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.whales = new ArrayList<>();
    }

    public Observation(int id, double longitude, double latitude, Date date){
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.whales = new ArrayList<>();
    }

    public void setId(int id) { this.id = id; }
    
    public void addWhale(Whale whale){
        whales.add(whale);
    }

    // These methods are used internally by Twirl helper form generator
    public void setLatitude(double lat){
        this.latitude = lat;
    }
    public void setLongitude(double longi){
        this.longitude = longi;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public ArrayList<Whale> getWhales(){
        return whales;
    }

    public int getId() { return this.id; }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public Date getDate(){
        return this.date;
    }

    public String getDateString(){
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        return dateformat.format(this.date);
    }
}
