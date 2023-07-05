package main.model;

public class Person {

    private int id;
    private int age;
    private String species;
    private String series;

    public Person() {
        id = 0;
        age = 0;
        species = "default";
        series = "default";
    }

    public Person(int id, int age, String species, String series) {
        this.id = id;
        this.age = age;
        this.species = species;
        this.series = series;
    }

    public int getID() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    public String getSeries() {
        return series;
    }
}
