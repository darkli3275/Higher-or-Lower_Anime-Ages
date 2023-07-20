package persistence;

import main.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.persistence.NetLoader.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NetLoaderTest {

    @BeforeEach
    void setUp() {
        initializeCharacterData();
    }

    @Test
    void personGetTest() {
        Person p = getPerson(0);
        assertEquals(p.getID(), 0);
        assertEquals(p.getAge(), 0);
        assertEquals(p.getSpecies(), "default");
        assertEquals(p.getSeries(), "default");
    }

    @Test
    void getImagePathTest0() {
        String imgPath = getImagePath(0);
        assertEquals(imgPath, "data/game/test.png");
    }

    @Test
    void getImagePathTestNegative1() {
        String imgPath = getImagePath(-1);
        assertEquals(imgPath, "data/game/test.png");
    }

    @Test
    void getImagePathTestBigPositive() {
        String imgPath = getImagePath(999999999);
        assertEquals(imgPath, "data/game/test.png");
    }

    /*
    @Test
    void initializeCharacterDataFileNotFoundTest() {
        initializeCharacterData("./data/game/Character.json");
    }*/

    // getNetImageTest()
    public static void main (String[] args) {
        initializeCharacterData();
        JFrame frame = new JFrame("TEST");;

        Person p = getPerson(0);
        BufferedImage img = (BufferedImage) getNetImage(p.getID());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }
}
