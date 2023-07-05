package persistence;

import main.model.PlayerData;
import org.junit.jupiter.api.Test;

import static main.persistence.LocalLoader.initializePlayerData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalLoaderTest {

    @Test
    void initializePlayerDataTest() {
        PlayerData pd = initializePlayerData("./data/game/Scores.json");
        // score, prev, high, total
        assertEquals(pd.getScore(), 0);
        assertEquals(pd.getPrevScore(), 10);
        assertEquals(pd.getHighScore(), 20);
        assertEquals(pd.getTotalScore(), 1200);
    }
}
