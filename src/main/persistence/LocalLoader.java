package main.persistence;

import main.model.PlayerData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Loads High Score Info
public class LocalLoader {

    // EFFECTS: Loads prev_score, high_score, total_score from save file path, null if error
    public static PlayerData initializePlayerData(String path) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject pdata = (JSONObject) parser.parse(new FileReader(path));

            int prev_score = (int) (long) pdata.get("prev_score");
            int high_score = (int) (long) pdata.get("high_score");
            int total_score = (int) (long) pdata.get("total_score");

            return new PlayerData(prev_score, high_score, total_score);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
