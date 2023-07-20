package persistence;

import main.model.PlayerData;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.persistence.LocalLoader.getLocalImage;
import static main.persistence.LocalLoader.initializePlayerData;
import static main.persistence.NetLoader.initializeCharacterData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalLoaderTest {

    @Test
    void initializePlayerDataTest() {
        PlayerData pd = initializePlayerData();
        // score, prev, high, total
        assertEquals(pd.getScore(), 0);
        assertEquals(pd.getPrevScore(), 10);
        assertEquals(pd.getHighScore(), 20);
        assertEquals(pd.getTotalScore(), 1200);
    }

    // getLocalImageTest
    public static void main (String[] args) {
        initializeCharacterData();
        JFrame frame = new JFrame("TEST");;

        BufferedImage img = (BufferedImage) getLocalImage("startPageBG.png");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }
}
