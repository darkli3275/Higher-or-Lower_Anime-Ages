package main.persistence;

import main.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// An abstraction of a database and its methods
public class NetLoader {

    public static final String CHARACTERDATAPATH = "data/game/CharacterData.json";
    private static final String IMAGESPATH = "data/game/CharacterImages/";
    public static List<Person> person_mp = new ArrayList<Person>();    // index 0 reserved for test AND intended to be consistent with path_mp

    /*
    // array of paths to images
    // intended to be consistent with data/game/CharacterData.json
    private static final String[] path_mp = new String[]{
            "./data/game/test.png"
    };
    */

    // REQUIRE: Call Upon Startup
    public static void initializeCharacterData() {
        // "./data/game/CharacterData.json"
        JSONParser parser = new JSONParser();

        try {
            JSONArray cdata = (JSONArray) parser.parse(new FileReader(CHARACTERDATAPATH));

            for (Object o : cdata) {
                JSONObject person = (JSONObject) o;

                int id = (int) (long) person.get("id");
                String name = (String) person.get("name");
                int age = (int) (long) person.get("age");
                String species = (String) person.get("species");
                String series = (String) person.get("series");
                String image_file = (String) person.get("image_file");

                person_mp.add(new Person(id, name, age, species, series, image_file));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: returns random Person excluding those with id's in avoidList.
    //          null if avoidList covers person_mp entirely
    public static Person getRandomPerson(List<Integer> avoidList) {
        int collisions = 0;
        int randID = generateRandomLegalID();

        while (avoidList.contains(randID) && collisions <= getMapSize()-1) {
            collisions++;
            randID++;
            if (randID >= getMapSize()) randID = 1;
        }

        if (collisions == getMapSize()) return null;
        return getPerson(randID);
    }

    // EFFECTS: returns random Person
    public static Person getRandomPerson() {
        int randID = generateRandomLegalID();
        return getPerson(randID);
    }

    // REQUIRE: initializeCharacterData called beforehand
    // EFFECTS: returns character associated with id, null if non-existent or error
    public static Person getPerson(int id) {
       if (idValid(id)) {
           Person p = person_mp.get(id);
           return new Person(p.getID(), p.getName(), p.getAge(), p.getSpecies(), p.getSeries(), p.getImageFile());
       } else {
           return null;
       }
    }

    // EFFECTS: return path to image of character associated with id, path to test image if id error
    public static String getImagePath(int id) {
        Person p;
        if (idValid(id)) {
            p = person_mp.get(id);
        } else {
            p = person_mp.get(0);
        }
        return IMAGESPATH + p.getImageFile();
    }

    // EFFECTS: returns the image associated with path, null if image not found
    public static Image getNetImage(String file_name) {
        BufferedImage i = null;
        try {
            i = ImageIO.read(new File(IMAGESPATH + file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }

    private static int generateRandomLegalID() {
        Random rand = new Random();
        int randID = rand.nextInt(getMapSize());
        if (randID == 0) randID = 1;
        return randID;
    }

    private static int getMapSize() {
        return person_mp.size();
    }

    private static boolean idValid(int id) {
        return (id >= 0 && id < getMapSize());
    }
}
