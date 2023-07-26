package main.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiComponent.*;

public class EndPage extends Page {

    private final String bgFileName = "endPageBG.png";
    private ArrayList<Component> updatableComponents;

    private final int vertSpace = HEIGHT/60, horzSpace = WIDTH/50;     // arbitrary small space
    private final int
            scoreTextSize = HEIGHT/12,                      scoreBoxWidth = WIDTH,
            scoreBoxHeight = (int) (scoreTextSize*1.25),     scoreY = HEIGHT/6,

            highScoreTextSize = HEIGHT/25,                  highScoreBoxHeight = (int) (highScoreTextSize*1.25),
            highScoreY = HEIGHT - 2*highScoreBoxHeight - vertSpace,

            buttonTextSize = HEIGHT/25, buttonBoxWidth = WIDTH/6, buttonBoxHeight = (int) (2*buttonTextSize*1.25),
                    buttonY = HEIGHT/2, menuButtonX = WIDTH/2 - (int) (buttonBoxWidth*1.5), playAgainX = WIDTH/2 + buttonBoxWidth/2;

    public EndPage(GameManager gm) {
        super();
        this.gm = gm;
        setup();
    }

    @Override
    public void setup() {
        updatableComponents = new ArrayList<>();
        setupButtons();
        setupBG();
    }

    private void setupScoresText() {
        JPanel panel = makePanel();

        JLabel score = (JLabel) makeText("You scored: " + gm.getPlayerData().getScore(),
                scoreTextSize, Color.WHITE, WIDTH/2 - scoreBoxWidth/2, scoreY, scoreBoxWidth, scoreBoxHeight);

        JLabel highScore = (JLabel) makeText("High Score: " + gm.getPlayerData().getHighScore(), highScoreTextSize,
                Color.WHITE, horzSpace, highScoreY, WIDTH/2, highScoreBoxHeight);
        highScore.setHorizontalAlignment(JLabel.LEFT);

        updatableComponents.add(panel);

        panel.add(score);
        panel.add(highScore);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void setupButtons() {
        JPanel panel = makePanel();

        JButton menu = makeButton("Menu", buttonTextSize, e -> backToMenu(),
                menuButtonX, buttonY, buttonBoxWidth, buttonBoxHeight);

        JButton playAgain = makeButton("Play Again", buttonTextSize, e -> playAgain(),
                playAgainX, buttonY, buttonBoxWidth, buttonBoxHeight);

        panel.add(menu);
        panel.add(playAgain);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    protected void gameOver() {
        for (Component component : updatableComponents) {
            pane.remove(component);
        }

        setupScoresText();
    }

    private void playAgain() {
        gm.newGame();
    }

    private void backToMenu() {
        next.show();
    }

    private void setupBG() {
        bg = (JLabel) makeBG(false, false, true, bgFileName, 0, 0, WIDTH, HEIGHT);
        pane.add(bg, -1);
    }

    @Override
    public void show() {
        gm.showPage(pane);
    }
}