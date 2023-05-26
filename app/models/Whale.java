package models;
import play.data.validation.Constraints;

/**
 * A basic whale class
 */

public class Whale {

  public int id = -1;

  @Constraints.Required
  public Species species;

  @Constraints.Min(0)
  public int weight;

  @Constraints.Required
  public Gender gender;

  public Whale(){

  }

  public Whale(String species, int weight, String gender){
    this.species = Species.valueOf(species);
    this.weight = weight;
    this.gender = Gender.valueOf(gender);
  }

  public Whale(int id, String species, int weight, String gender){
    this.id = id;
    this.species = Species.valueOf(species);
    this.weight = weight;
    this.gender = Gender.valueOf(gender);
  }

  /**
   * Getters
   */
   
  public int getId() {
    return id;
  }
  public int getWeight() {
    return weight;
  }
  public String getGender() {
    return gender.toString();
  }
  public String getSpecies() {
    return species.toString();
  }

  /**
   * Setters
   */
  public void setId(int id) {
    this.id = id;
  }
  public void setWeight(int weight) {
    this.weight = weight;
  }
  public void setGender(String gen) {
    this.gender = Gender.valueOf(gen);
  }
  public void setSpecies(String species) {
    this.species = Species.valueOf(species);
  }

  /**
   * Enums
   */
  public enum Gender {
    MALE,
    FEMALE
  }

  public enum Species {
    ORCA,
    PORPOISE,
    HUMPBACK,
    KILLER,
    BLUE
  }
}