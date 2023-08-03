/**
 * An abstraction of a database and its interface.
 * A collection of methods involving retrieving data stored "virtually".

 * @author Raymond Li
 */

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

public class NetLoader {

    public static final String CHARACTER_DATA_PATH = "data/game/CharacterData.json";
    private static final String IMAGES_PATH = "data/game/CharacterImages/";

    // Simulated database of game's character data
    public static List<Person> person_mp = new ArrayList<Person>();
    // List of file names corresponding to characters in person_mp
    private static List<String> img_file_mp = new ArrayList<>();

    // TODO Handle Potential Failure
    // REQUIRE: Call upon game initialization
    // EFFECTS: Loads character data and image file paths into game
    public static void initializeCharacterData() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray cdata = (JSONArray) parser.parse(new FileReader(CHARACTER_DATA_PATH));

            for (Object o : cdata) {
                JSONObject person = (JSONObject) o;

                int id = (int) (long) person.get("id");
                String name = (String) person.get("name");

                double age = -1;
                try {
                    age = (double) (long) person.get("age");
                } catch (Exception e) {
                    age = (double) person.get("age");
                }
                if (age < 0) {
                    age = 0;
                }

                String species = (String) person.get("species");
                String series = (String) person.get("series");
                String image_file = (String) person.get("image_file");

                person_mp.add(new Person(id, name, age, species, series));
                img_file_mp.add(image_file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // REQUIRE: initializeCharacterData called beforehand
    // EFFECTS: Returns character associated with id, null if non-existent or error
    public static Person getPerson(int id) {
        if (idValid(id)) {
            Person p = person_mp.get(id);
            return new Person(p.getID(), p.getName(), p.getAge(), p.getSpecies(), p.getSeries());
        } else {
            return null;
        }
    }

    // EFFECTS: returns random Person excluding those with id's in avoidList.
    //          null if avoidList covers person_mp entirely
    public static Person getRandomPerson(List<Integer> avoidList) {
        int collisions = 0;
        int randID = generateRandomLegalID();

        while (avoidList.contains(randID) && collisions <= getPersonMapSize()-1) {
            collisions++;
            randID++;
            if (randID >= getPersonMapSize()) randID = 1;
        }

        if (collisions == getPersonMapSize()) return null;
        return getPerson(randID);
    }

    // EFFECTS: Returns random Person
    public static Person getRandomPerson() {
        int randID = generateRandomLegalID();
        return getPerson(randID);
    }

    // EFFECTS: Returns the image associated with character id. Returns null if image not found.
    public static Image getNetCharacterImage(int id) {
        BufferedImage i = null;
        try {
            i = ImageIO.read(new File(IMAGES_PATH + getImagePath(id)));
        } catch (IOException e) {
            System.out.println(id);
            e.printStackTrace();
        }

        return i;
    }

    // EFFECTS: Returns path to image of character associated with id. Returns null if error.
    public static String getImagePath(int id) {
        if (idValid(id)) {
            return img_file_mp.get(id);
        }
        return null;
    }

    // EFFECTS: Returns a random legal character ID. Returns 1 if ID 0 is generated.
    private static int generateRandomLegalID() {
        Random rand = new Random();
        int randID = rand.nextInt(getPersonMapSize());
        if (randID == 0) randID = 1;
        return randID;
    }

    // EFFECTS: Returns true if id is within bounds of person_mp's size
    private static boolean idValid(int id) {
        return (id >= 0 && id < getPersonMapSize());
    }

    private static int getPersonMapSize() {
        return person_mp.size();
    }
}
