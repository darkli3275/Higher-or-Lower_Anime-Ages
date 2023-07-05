package main.ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainWindow {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int WIDTH = screenSize.width / 2;    //TODO
    public int HEIGHT = screenSize.height / 2;  //TODO
    private JFrame window;

    public MainWindow() {
        window = new JFrame();
        window.setTitle("Higher or Lower? - Anime Ages");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);             //TODO
    }

    public void show() {
        window.setVisible(true);
    }
}
