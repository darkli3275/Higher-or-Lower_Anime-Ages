/**
 * The class containing everything game window-related
 * and launches the GameManager.
 *
 * @author Raymond Li
 */

package main.ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainWindow {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    private JFrame window;
    private GameManager gm;

    // EFFECTS: Creates and shows game window
    public MainWindow() {
        WIDTH = (int) (screenSize.width / 1.5);
        HEIGHT = (int) (screenSize.height / 1.5);

        window = new JFrame();
        window.setTitle("Higher or Lower? - Anime Ages");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    public void show() {
        gm = new GameManager(window);
        gm.showStartPage();
    }
}