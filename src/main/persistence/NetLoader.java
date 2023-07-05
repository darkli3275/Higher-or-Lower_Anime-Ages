package main.persistence;

import main.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetLoader {

    public static List<Person> person_mp = new ArrayList<Person>();    // index 0 reserved for test AND intended to be consistent with path_mp

    // array of paths to images
    // intended to be consistent with data/game/CharacterData.json
    private static final String[] path_mp = new String[]{
            "./data/game/test.png"
    };

    // REQUIRE: Call Upon Startup
    public static void initializeCharacterData(String path) {
        // "./data/game/CharacterData.json"
        JSONParser parser = new JSONParser();

        try {
            JSONArray cdata = (JSONArray) parser.parse(new FileReader(path));

            for (Object o : cdata) {
                JSONObject person = (JSONObject) o;

                int id = (int) (long) person.get("id");
                int age = (int) (long) person.get("age");
                String species = (String) person.get("species");
                String series = (String) person.get("series");

                person_mp.add(new Person(id, age, species, series));
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
    // EFFECTS: returns character associated with id, null if non-existent or error
    public static Person personGet(int id) {
       if (idValid(id)) {
           Person p = person_mp.get(id);
           return new Person(p.getID(), p.getAge(), p.getSpecies(), p.getSeries());
       } else {
           return null;
       }
    }

    // EFFECTS: return path to image of character associated with id, path to test image if id error
    public static String getImagePath(int id) {
        if (idValid(id)) {
            return path_mp[id];
        } else {
            return path_mp[0];
        }
    }

    private static int getMapSize() {
        return path_mp.length;
    }

    private static boolean idValid(int id) {
        return (id >= 0 && id < getMapSize());
    }
}
