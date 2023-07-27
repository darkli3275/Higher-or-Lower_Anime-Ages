/**
 * A collection of methods involving storing data.
 *
 * @author Raymond Li
 */

package main.persistence;

import main.model.PlayerData;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Saver {

    private static final String SAVE_FILE = "data/local/Scores.json";

    // EFFECTS: Saves player data to save file. Returns true if save successful.
    public static boolean saveScore(PlayerData pd) {
        JSONObject pdjson = pd.playerDataToJSON();

        try {
            FileWriter file = new FileWriter(SAVE_FILE);
            file.write(pdjson.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
