package model;

import static org.junit.Assert.assertEquals;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
public class ObservationTest {
    Observation observation;
    Whale whale;

    Date date = new Date(120,11,15);
    @Before
    public void setUp() throws Exception
    {
        whale = new Whale(100, "KILLER", 16000, "FEMALE");

        observation = new Observation(32.6,52.7,date);
    }

    @Test
    public void testSetIdGetID()
    {
        observation.setId(100);
        assertEquals(100,observation.getId());
    }
    @Test
    public void testAddWhalesGetWhales()
    {
        observation.addWhale(whale);
        assertEquals(true,observation.getWhales().contains(whale));

    }
    @Test
    public void testGetLatitude()
    {
        assertEquals(true,observation.getLatitude()==52.7);
    }
    @Test
    public void testSetLatitdue()
    {
        observation.setLatitude(50);
        assertEquals(true,observation.getLatitude()==50.0);
    }
    @Test
    public void testGetLongtitude()
    {
        assertEquals(true,observation.getLongitude()==32.6);
    }

    @Test
    public void testSetLongtitude()
    {
        observation.setLongitude(50);
        assertEquals(true, observation.getLongitude()==50.0);
    }

    @Test
    public void testGetDate()
    {
        assertEquals(date,observation.getDate());
    }


    @Test
    public void testSetDate()
    {
        Date dateTmp = new Date(120,2,15);
        observation.setDate(dateTmp);
        assertEquals(dateTmp,observation.getDate());
    }
    @Test
    public void testGetDateString()
    {
        observation.setDate(date);
        assertEquals("2020-12-15",observation.getDateString());
    }





}
