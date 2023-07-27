/**
 * Collection of methods involving retrieving data stored locally.
 *
 * @author Raymond Li
 */

package main.persistence;

import main.model.PlayerData;
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

public class LocalLoader {

    public static final String PLAYER_DATA_PATH = "data/local/Scores.json";
    public static final String LOCAL_PATH = "data/local/";

    // EFFECTS: Loads and returns saved player data, returns null if error
    public static PlayerData initializePlayerData() {
        JSONParser parser = new JSONParser();

        try {
            JSONObject pdata = (JSONObject) parser.parse(new FileReader(PLAYER_DATA_PATH));

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

    // EFFECTS: Loads and returns image stored locally. Returns null if unsuccessful
    public static Image getLocalImage(String file_name) {
        BufferedImage i = null;

        try {
            i = ImageIO.read(new File(LOCAL_PATH + file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }
}
