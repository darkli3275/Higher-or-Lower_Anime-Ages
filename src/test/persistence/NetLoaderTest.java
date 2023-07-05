package persistence;

import main.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.persistence.NetLoader.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NetLoaderTest {

    @BeforeEach
    void setUp() {
        initializeCharacterData("./data/game/CharacterData.json");
    }

    @Test
    void personGetTest() {
        Person p = personGet(0);
        assertEquals(p.getID(), 0);
        assertEquals(p.getAge(), 0);
        assertEquals(p.getSpecies(), "default");
        assertEquals(p.getSeries(), "default");
    }

    @Test
    void getImagePathTest0() {
        String imgPath = getImagePath(0);
        assertEquals(imgPath, "./data/game/test.png");
    }

    @Test
    void getImagePathTestNegative1() {
        String imgPath = getImagePath(-1);
        assertEquals(imgPath, "./data/game/test.png");
    }

    @Test
    void getImagePathTestBigPositive() {
        String imgPath = getImagePath(999999999);
        assertEquals(imgPath, "./data/game/test.png");
    }

    @Test
    void initializeCharacterDataFileNotFoundTest() {
        initializeCharacterData("./data/game/Character.json");
    }
}
