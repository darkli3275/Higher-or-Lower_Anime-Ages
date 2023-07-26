package main.model;

public class Person {

    private int id;
    private String name;
    private int age;
    private String species;
    private String series;
    private String image_file;

    public Person() {
        id = 0;
        name = "default";
        age = 0;
        species = "default";
        series = "default";
        image_file = "test.png";
    }

    public Person(int id, String name, int age, String species, String series, String image_file) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.species = species;
        this.series = series;
        this.image_file = image_file;
    }

    public int getID() {
        return id;
    }

    public String getName() {return name;}

    public int getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    public String getSeries() {
        return series;
    }

    public String getImageFile() {return image_file;}
}
