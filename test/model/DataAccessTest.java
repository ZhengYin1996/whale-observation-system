package model;
import junit.framework.TestCase;
import models.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import com.typesafe.config.Config;
import org.junit.Before;
import org.junit.Test;
import java.sql.Date;

import java.util.ArrayList;

public class DataAccessTest {
    DataAccess dataAccess = new DataAccess("jdbc:h2:mem:play;MODE=MYSQL;DB_CLOSE_DELAY=-1");
    ArrayList<Whale> w;
    ArrayList<Observation> observations = new ArrayList<Observation>();
    Observation observation;
    @Before
    public void setUp()
    {
        w = new ArrayList<>();



        w.add(new Whale("ORCA", 1000, "MALE"));
        w.add(new Whale("PORPOISE", 2000, "MALE"));
        w.add(new Whale("KILLER", 4000, "MALE"));
        w.add(new Whale("ORCA", 5000, "MALE"));

        w.add(new Whale("PORPOISE", 1100, "FEMALE"));
        w.add(new Whale("PORPOISE", 2000, "FEMALE"));
        w.add(new Whale("PORPOISE", 4000, "FEMALE"));
        w.add(new Whale("PORPOISE", 5000, "FEMALE"));

        ArrayList<Whale> w1 = new ArrayList<>();
        ArrayList<Whale> w2 = new ArrayList<>();
        Date date = Date.valueOf("2020-10-01");

        w1.add(new Whale("ORCA", 1000, "MALE"));
        w1.add(new Whale("PORPOISE", 2000, "MALE"));
        w1.add(new Whale("KILLER", 4000, "MALE"));
        w1.add(new Whale("ORCA", 5000, "MALE"));
        observation = new Observation(10, 10, date, w1);
        w2.add(new Whale("PORPOISE", 1100, "FEMALE"));
        w2.add(new Whale("PORPOISE", 2000, "FEMALE"));
        w2.add(new Whale("PORPOISE", 4000, "FEMALE"));
        w2.add(new Whale("PORPOISE", 5000, "FEMALE"));
        observations.add(observation);
        observations.add(new Observation(25,23, new java.util.Date(), w2));
    }
    /**
     * Test for delete All functionality
     * expected database to be empty
     */
    @Test
    public void testDeleteMethod() {
        dataAccess.deleteAll();
        assertTrue(dataAccess.getAllObservations().isEmpty());
    }
    @Test
    public void testInsertObservation() {
        dataAccess.insertObservation(observation);
        assertEquals(1, dataAccess.getAllObservations().size());
        dataAccess.deleteAll();
    }

    @Test
    public void testGetAllWhales()
    {
        ArrayList<Whale> whales = dataAccess.getAllWhales();
        for(int i =0; i<whales.size();i++)
        {
            assertEquals(w.get(i).getSpecies(),whales.get(i).getSpecies());
            assertEquals(w.get(i).getWeight(),whales.get(i).getWeight());
            assertEquals(w.get(i).getGender(),whales.get(i).getGender());
        }
    }

    @Test
    public void testGetAllObservation()
    {
        ArrayList<Observation> allObservations = dataAccess.getAllObservations();
        for(int i = 0; i <allObservations.size();i++)
        {
            assertEquals(true,observations.get(i).getLatitude()==allObservations.get(i).getLatitude());
            assertEquals(true,observations.get(i).getLongitude()==allObservations.get(i).getLongitude());
            for(int j = 0;j<allObservations.get(i).getWhales().size();j++)
            {
                Whale whale1 = observations.get(i).getWhales().get(j);
                Whale whale2 = allObservations.get(i).getWhales().get(j);
                assertEquals(whale1.getGender(),whale2.getGender());
                assertEquals(whale1.getWeight(),whale2.getWeight());
                assertEquals(whale1.getSpecies(),whale2.getSpecies());
            }

        }
    }





}
