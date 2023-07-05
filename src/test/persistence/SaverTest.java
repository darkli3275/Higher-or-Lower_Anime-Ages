package persistence;

import main.model.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.persistence.Saver.saveScore;

public class SaverTest {
    PlayerData pd;

    @BeforeEach
    void setUp() {
        pd = new PlayerData(10, 20, 1200);
    }

    @Test
    void saveScoreTest() {
        saveScore(pd);
    }
}
