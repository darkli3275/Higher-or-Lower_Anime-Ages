package main.ui;

import javax.swing.*;
import java.awt.*;
import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiComponent.*;

public class StartPage extends Page {

    public StartPage() {
        super();
        setup();
    }

    @Override
    public void setup() {
        setupButtons();
        setupText();
        setupBG();
    }

    private void setupBG() {
        bg = (JLabel) makeBG("startPageBG.png");
        pane.add(bg, 3);
    }

    private void setupText() {
        JPanel panel = makePanel();

        JLabel titleText = (JLabel) makeText("Higher or Lower? Anime Ages!",
                75, Color.WHITE, 0,HEIGHT/4, WIDTH, HEIGHT/4);
        JLabel highScoreText = (JLabel) makeText("High Score: " + GameManager.playerData.getHighScore(),
                30, Color.WHITE, 0, HEIGHT - HEIGHT/5, WIDTH, HEIGHT/5);

        panel.add(titleText);
        panel.add(highScoreText);
        pane.add(panel, 1);
    }

    private void setupButtons() {
        JPanel panel = makePanel();
        int buttonWidth = WIDTH/6;
        int buttonHeight = HEIGHT/10;
        int buttonX = WIDTH/2 - buttonWidth/2;
        int exitY = HEIGHT - HEIGHT/3;

        JButton startButton = makeButton("Start!", 30, e -> GameManager.showPage(next.getPane()),
                buttonX, exitY - (int) (1.25 * buttonHeight), buttonWidth, buttonHeight);
        JButton exitButton = makeButton("Exit", 30, e -> GameManager.saveAndQuit(),
                buttonX, exitY, buttonWidth, buttonHeight);

        panel.add(startButton);
        panel.add(exitButton);
        pane.add(panel, 0);
    }
}
