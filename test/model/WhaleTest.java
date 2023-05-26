package model;


import static org.junit.Assert.assertEquals;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
public class WhaleTest {
    Whale whale;
    @Before
    public void setUp()
    {
        whale=new Whale(100, "KILLER", 16000, "FEMALE");
    }

    @Test
    public void testGetId()
    {
        assertEquals(100,whale.getId());
    }

    @Test
    public void testSetId()
    {
        whale.setId(50);
        assertEquals(50,whale.id);
    }

    @Test
    public void testGetWeight()
    {
        assertEquals(16000,whale.getWeight());
    }

    @Test
    public void testSetWeight()
    {
        whale.setWeight(12000);
        assertEquals(12000,whale.weight);
    }

    @Test
    public void testGetGender()
    {
        assertEquals("FEMALE",whale.getGender());
    }

    @Test
    public void testSetGender()
    {
        whale.setGender("MALE");
        assertEquals("MALE",whale.getGender());
    }
    @Test
    public void testGetSpecies()
    {
        assertEquals("KILLER",whale.getSpecies());
    }

    @Test
    public void testSetSpecies()
    {
        whale.setSpecies("ORCA");
        assertEquals("ORCA",whale.getSpecies());
    }
}
