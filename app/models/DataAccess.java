package models;

import java.util.ArrayList;

import java.sql.*;

/**
 * This class is the Data Access Layer of our application.
 * It deals with connecting to, extracting from, and writing to our local H2 SQL database.
 */

public class DataAccess {
    private final String dbUrl;

    public DataAccess(String dbUrl) {
        this.dbUrl = dbUrl;
        createTables();

        /* TESTING DATA
        deleteAll();
        ArrayList<Whale> w1 = new ArrayList<>();
        ArrayList<Whale> w2 = new ArrayList<>();


        w1.add(new Whale("ORCA", 1000, "MALE"));
        w1.add(new Whale("PORPOISE", 2000, "MALE"));
        w1.add(new Whale("KILLER", 4000, "MALE"));
        w1.add(new Whale("ORCA", 5000, "MALE"));

        w2.add(new Whale("PORPOISE", 1100, "FEMALE"));
        w2.add(new Whale("PORPOISE", 2000, "FEMALE"));
        w2.add(new Whale("PORPOISE", 4000, "FEMALE"));
        w2.add(new Whale("PORPOISE", 5000, "FEMALE"));

        insertObservation(new Observation(12,12, new java.util.Date(), w1));
        insertObservation(new Observation(25,23, new java.util.Date(), w2));
        */

    }

    /**
    * Generates tables in the H2 database if they dont exist
    */
    private void createTables() {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement stm = conn.createStatement()) {

            stm.execute(
                    "CREATE TABLE IF NOT EXISTS OBSERVATIONS " +
                            "(id INTEGER not NULL AUTO_INCREMENT, " +
                            " longitude DOUBLE, " +
                            " latitude DOUBLE, " +
                            " date DATE, " +
                            " PRIMARY KEY ( id ))" );

            stm.execute(
                    "CREATE TABLE IF NOT EXISTS WHALES " +
                            "(id INTEGER not NULL AUTO_INCREMENT, " +
                            " obsid INTEGER not NULL," +
                            " species VARCHAR(255), " +
                            " gender VARCHAR(255), " +
                            " weight INTEGER, " +
                            " PRIMARY KEY ( id )," +
                            " FOREIGN KEY (obsid) REFERENCES OBSERVATIONS(id))" );

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
    * Deletes all of the observations and whales from the database
    */
    public void deleteAll() {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement stm = conn.createStatement()) {

            stm.execute("DELETE FROM WHALES; " +
                    "DELETE FROM OBSERVATIONS;");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
    * Given an Observation object inserts it and the given whales into their
    * respective databases
    */
    public void insertObservation(Observation observation) throws IllegalArgumentException {
        if (observation.getWhales().size() == 0)
            throw new IllegalArgumentException("Observations in the database must have whales");

        try (
                Connection conn = DriverManager.getConnection(dbUrl)) {

            conn.setAutoCommit(false);
            PreparedStatement observationInsert = conn.prepareStatement(
                    "INSERT INTO OBSERVATIONS(longitude, latitude, date) values(?, ?, ?); " +
                            "SET @last_inserted_observation = LAST_INSERT_ID();");
            observationInsert.setDouble(1, observation.getLongitude());
            observationInsert.setDouble(2, observation.getLatitude());
            observationInsert.setString(3, observation.getDateString());
            observationInsert.executeUpdate();

            PreparedStatement whaleInsert = conn.prepareStatement(
                    "INSERT INTO WHALES(obsid, species, gender, weight) values(@last_inserted_observation,?,?,?);");
            for (Whale whale : observation.getWhales()) {
                whaleInsert.setString(1, whale.getSpecies());
                whaleInsert.setString(2, whale.getGender());
                whaleInsert.setInt(3, whale.getWeight());
                whaleInsert.addBatch();
            }
            whaleInsert.executeBatch();

            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
    * Gets all of the Whales in the database
    */
    public ArrayList<Whale> getAllWhales() {
        ArrayList<Whale> whales = new ArrayList<>();
        try ( Connection conn = DriverManager.getConnection(dbUrl);
              Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        ) {
            ResultSet rs = stm.executeQuery(
                    "SELECT o.id as oid, w.id as wid, o.latitude, o.longitude, o.date, " +
                            "w.species, w.gender, w.weight " +
                            "FROM OBSERVATIONS o " +
                            "LEFT JOIN whales w " +
                            "ON o.id = w.obsid " +
                            "ORDER BY w.id ");
            while (rs.next()) {
                Whale whale = new Whale(rs.getInt("wid"), rs.getString("species"), rs.getInt("weight"), rs.getString("gender"));
                whales.add(whale);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return whales;
    }

     /**
    * Gets all of the Observations from the database including the Whales
    * in the Whales table associated with those Observations
    */
    public ArrayList<Observation> getAllObservations() {
        ArrayList<Observation> observations = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            ResultSet rs = stm.executeQuery(
                    "SELECT o.id as oid, w.id as wid, o.latitude, o.longitude, o.date, " +
                            "w.species, w.gender, w.weight " +
                            "FROM OBSERVATIONS o " +
                            "LEFT JOIN whales w " +
                            "ON o.id = w.obsid " +
                            "ORDER BY o.id ");

            while ( rs.next() ) {
                int id = rs.getInt("oid");
                Observation observation = new Observation(rs.getInt("oid"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getDate("date"));
                Whale whale = new Whale(rs.getInt("wid"), rs.getString("species"), rs.getInt("weight"), rs.getString("gender"));
                observation.addWhale(whale);

                // See if there are more whales in this observation
                while (rs.next()) {
                    if (rs.getInt("oid") == id) {
                        Whale nextWhale = new Whale(rs.getInt("wid"), rs.getString("species"), rs.getInt("weight"), rs.getString("gender"));
                        observation.addWhale(nextWhale);
                    } else {
                        rs.previous();
                        break;
                    }
                }
                observations.add(observation);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return observations;
    }
}