/**
 * A Higher or Lower game about guessing characters' ages.
 * This class launches the game.
 * 
 * @author Raymond Li
 */

import main.ui.MainWindow;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show();
            }
        });
    }
}