package main.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiComponent.*;

public class StartPage extends Page {

    private final String bgFileName = "startPageBG.png";

    private final int bigTextSize = 75;
    private final int medTextSize = 30;
    int highScoreBoxHeight = (int) (1.5*medTextSize);
    int titleBoxHeight = (int) (1.5*bigTextSize);
    private ArrayList<Component> updatableComponents;

    public StartPage(GameManager gm) {
        super();
        this.gm = gm;
        updatableComponents = new ArrayList<>();
        setup();
    }

    @Override
    public void setup() {
        setupButtons();
        setupStaticText();
        setupBG();
    }

    private void setupBG() {
        bg = (JLabel) makeBG(false, false, false, bgFileName, 0, 0, WIDTH, HEIGHT);
        pane.add(bg, -1);
    }

    private void setupStaticText() {
        JPanel panel = makePanel();

        JLabel titleText = (JLabel) makeText("Higher or Lower? Anime Ages!",
                bigTextSize, Color.WHITE, 0,HEIGHT/2 - titleBoxHeight, WIDTH, titleBoxHeight);

        panel.add(titleText);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void setupButtons() {
        JPanel panel = makePanel();
        int buttonWidth = WIDTH/6;
        int buttonHeight = HEIGHT/10;
        int buttonX = WIDTH/2 - buttonWidth/2;
        int exitY = HEIGHT - HEIGHT/3;

        JButton startButton = makeButton("Start!", 30, e -> startButtonAction(),
                buttonX, exitY - (int) (1.25 * buttonHeight), buttonWidth, buttonHeight);
        JButton exitButton = makeButton("Exit", 30, e -> gm.saveAndQuit(),
                buttonX, exitY, buttonWidth, buttonHeight);

        panel.add(startButton);
        panel.add(exitButton);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void setupScoresText() {
        JPanel panel = makePanel();
        JLabel highScoreText = (JLabel) makeText("High Score: " + gm.getPlayerData().getHighScore(),
                medTextSize, Color.WHITE, 0, HEIGHT - 2*highScoreBoxHeight, WIDTH, highScoreBoxHeight);

        panel.add(highScoreText);
        updatableComponents.add(panel);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void updateComponents() {
        for (Component component : updatableComponents) {
            pane.remove(component);
        }

        setupScoresText();
    }

    private void startButtonAction() {
        gm.newGame();
    }

    @Override
    public void show() {
        updateComponents();
        gm.showPage(pane);
    }
}
