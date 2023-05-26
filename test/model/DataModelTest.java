package model;

import static org.junit.Assert.assertEquals;

import com.typesafe.config.Config;
import models.*;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;

import java.lang.*;
import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;


public class DataModelTest {
    Whale whale = new Whale(100, "KILLER", 16000, "FEMALE");
    Field currentWhales;
    ArrayList<Whale> currentWhalesArrayList, allWhalesArraylist;
    ArrayList<Observation> allObservationsArrayList;
    Field allWhales;
    Field allObservations;
    DataModel dataModel = new DataModel("jdbc:h2:mem:play;MODE=MYSQL;DB_CLOSE_DELAY=-1");
    String JsonCWA, JsonAWA, JsonAOA;
    ArrayList<Whale> wArrayList;
    Whale w1,w2;
    @Before
    public void setUp () throws ClassNotFoundException, NoSuchFieldException,IllegalAccessException
    {

        Class c1 = DataModel.class;
        currentWhales = c1.getDeclaredField("currentWhales");
        allWhales = c1.getDeclaredField("allWhales");
        allObservations = c1.getDeclaredField("allObservations");

        currentWhales.setAccessible(true);
        allWhales.setAccessible(true);
        allObservations.setAccessible(true);
        currentWhalesArrayList =(ArrayList<Whale>) currentWhales.get(dataModel);
        allWhalesArraylist = (ArrayList<Whale>) allWhales.get(dataModel);
        allObservationsArrayList = (ArrayList<Observation>) allObservations.get(dataModel);
        w1 = new Whale(18,"KILLER",13000,"MALE");
        w2 = new Whale(23,"ORCA",16000,"FEMALE");
        wArrayList= new ArrayList<Whale>();
        wArrayList.add(w1);
        wArrayList.add(w2);
    }


    @Test
    public void testAddWhaleToCurrent ()
    {
        dataModel.addWhaleToCurrent(whale);
        assertEquals(true,currentWhalesArrayList.contains(whale));
    }


    @Test
    public void testdeleteAllObservations()
    {
        dataModel.deleteAllObservations();
        assertEquals(true,allObservationsArrayList.isEmpty());
    }

    @Test
    public void testClearCurrentWhales()
    {
        dataModel.clearCurrentWhales();
        assertEquals(true,currentWhalesArrayList.isEmpty());
    }
    @Test
    public void testGetAllObservationsJson()
    {
        JsonAOA = Json.stringify(Json.toJson(allObservationsArrayList));
        assertEquals(JsonAOA,dataModel.getAllObservationsJson());
    }
    @Test
    public void testGetCurrentWhalesJson()
    {
        JsonCWA = Json.stringify(Json.toJson(currentWhalesArrayList));
        assertEquals(JsonCWA,dataModel.getCurrentWhalesJson());
    }

    @Test
    public void testGetAllWhalesJson()
    {
        JsonAWA = Json.stringify(Json.toJson(allWhalesArraylist));
        assertEquals(JsonAWA,dataModel.getAllWhalesJson());
    }

    @Test
    public void testGetWhales()
    {
        assertEquals(currentWhalesArrayList,dataModel.getWhales());
    }

    @Test
    public void testSetAllObservations () throws  IllegalAccessException
    {
        Date date = new Date(120,11,15);
        Date dateTmp = new Date(120,2,15);
        ArrayList<Observation> OArrayList = new ArrayList<Observation>();
        Observation o1 = new Observation(20.5,20.5,date,wArrayList);
        OArrayList.add(o1);
        dataModel.insertObservation(o1);
        dataModel.setAllObservations();
        ArrayList<Observation> obList =new ArrayList<Observation>();
        obList.add(o1);
        try{
            Field db = dataModel.getClass().getDeclaredField("db");
            db.setAccessible(true);
            DataAccess dataAccess =(DataAccess) db.get(dataModel);
            ArrayList<Observation> arrayListTMP = dataAccess.getAllObservations();
            assertEquals(obList.get(0).getDateString(),arrayListTMP.get(0).getDateString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSetAllWhales() throws  IllegalAccessException
    {

        dataModel.addWhaleToCurrent(w1);
        dataModel.addWhaleToCurrent(w2);
        dataModel.setAllWhales();
        ArrayList<Whale> wList = dataModel.getWhales();
        assertEquals(wArrayList,wList);
    }
    @Test
    public void testArrayListToJson()
    {
        try{
            Method method = DataModel.class.getDeclaredMethod("arrayListToJson");
            method.setAccessible(true);
            String result = "[{\"id\":18,\"species\":\"KILLER\",\"weight\":13000,\"gender\":\"MALE\"},{\"id\":23,\"species\":\"ORCA\",\"weight\":16000,\"gender\":\"FEMALE\"}]";
            assertEquals(result,method.invoke(wArrayList));
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e2) {
            e2.printStackTrace();
        }
    }

}
