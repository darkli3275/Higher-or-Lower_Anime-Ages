package main.persistence;

import main.model.PlayerData;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// Saves High Score Info
public class Saver {

    public static void saveScore(PlayerData pd) {
        JSONObject pdjson = pd.playerDataToJSON();

        try {
            FileWriter file = new FileWriter("data/local/Scores.json");
            file.write(pdjson.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
