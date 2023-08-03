/**
 * Represents a character in the game. Each character is represented by a Person object.
 *
 * @author Raymond Li
 */

package main.model;

public class Person {

    private int id;
    private String name;
    private double age;
    private String species;
    private String series;

    public Person() {
        id = 0;
        name = "default";
        age = 0;
        species = "default";
        series = "default";
    }

    public Person(int id, String name, double age, String species, String series) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.species = species;
        this.series = series;
    }

    // Getters
    public int getID() {return id;}
    public String getName() {return name;}
    public double getAge() {return age;}
    public String getSpecies() {return species;}
    public String getSeries() {return series;}

    // Setters
    public void setID(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setAge(double age) {this.age = age;}
    public void setSpecies(String species) {this.species = species;}
    public void setSeries(String series) {this.series = series;}
}

/* Unused image_file code
    private String image_file;
    this.image_file = image_file;
    public String getImageFile() {return image_file;}
    public void setImageFile(String image_file) {this.image_file = image_file;}
 */
