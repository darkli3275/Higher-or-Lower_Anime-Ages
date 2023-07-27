/**
 * Game over screen with buttons to go back to the starting page or play again.
 *
 * @author Raymond Li
 */

package main.ui.Pages;

import main.ui.GameManager;

import javax.swing.*;
import java.awt.*;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiTools.*;

public class EndPage extends Page {

    private final String BG_FILE_NAME = "endPageBG.png";               // local image

    // Standard size and position values for screen elements.
    private final int
            // Arbitrary small space for minor positioning edits
            VERT_SPACE = HEIGHT/60,                         HORZ_SPACE = WIDTH/50,

            // Score
            SCORE_TEXT_SIZE = HEIGHT/12,                    SCORE_BOX_HEIGHT = (int) (SCORE_TEXT_SIZE *1.25),
            SCORE_BOX_WIDTH = WIDTH,                        SCORE_Y = HEIGHT/6,

            // High Score
            HIGH_SCORE_TEXT_SIZE = HEIGHT/25,               HIGH_SCORE_BOX_HEIGHT = (int) (HIGH_SCORE_TEXT_SIZE *1.25),
            HIGH_SCORE_Y = HEIGHT - 2* HIGH_SCORE_BOX_HEIGHT - VERT_SPACE,

            // Buttons
            BUTTON_TEXT_SIZE = HEIGHT/25,                   BUTTON_BOX_HEIGHT = (int) (2* BUTTON_TEXT_SIZE *1.25),
            BUTTON_BOX_WIDTH = WIDTH/6,                     BUTTON_Y = HEIGHT/2,
            MENU_BUTTON_X = WIDTH/2 - (int) (BUTTON_BOX_WIDTH *1.5),
            AGAIN_BUTTON_X = WIDTH/2 + BUTTON_BOX_WIDTH /2
            ;

    // EFFECTS: Creates a new EndPage with Game Manager gm
    public EndPage(GameManager gm) {
        super();
        this.gm = gm;
        setupUI();
    }

    // EFFECTS: Sets up the UI elements of the page
    @Override
    public void setupUI() {
        setupButtons();
        setupScoresText();
        setupBG();
    }

    // EFFECTS: Sets up the background image
    private void setupBG() {
        Image img = gm.getLocalImage(BG_FILE_NAME);
        img = scaleImage(img, WIDTH, HEIGHT);
        bg = (JLabel) makeBG(img);

        pane.add(bg, -1);
    }

    // EFFECTS: Sets up score and high Score text
    private void setupScoresText() {
        JPanel panel = makePanel();

        JLabel score = (JLabel) makeText("You scored: " + gm.getScore(), SCORE_TEXT_SIZE, Color.WHITE,
                WIDTH/2 - SCORE_BOX_WIDTH /2, SCORE_Y, SCORE_BOX_WIDTH, SCORE_BOX_HEIGHT);

        JLabel highScore = (JLabel) makeText("High Score: " + gm.getHighScore(), HIGH_SCORE_TEXT_SIZE, Color.WHITE,
                HORZ_SPACE, HIGH_SCORE_Y, WIDTH/2, HIGH_SCORE_BOX_HEIGHT);
        highScore.setHorizontalAlignment(JLabel.LEFT);

        panel.add(score);
        panel.add(highScore);
        stale_components.add(panel);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up page's buttons
    private void setupButtons() {
        JPanel panel = makePanel();

        JButton menu = makeButton("Menu", BUTTON_TEXT_SIZE, e -> backToMenu(),
                MENU_BUTTON_X, BUTTON_Y, BUTTON_BOX_WIDTH, BUTTON_BOX_HEIGHT);

        JButton playAgain = makeButton("Play Again", BUTTON_TEXT_SIZE, e -> playAgain(),
                AGAIN_BUTTON_X, BUTTON_Y, BUTTON_BOX_WIDTH, BUTTON_BOX_HEIGHT);

        panel.add(menu);
        panel.add(playAgain);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Starts a new game
    public void playAgain() {
        gm.newGame();
    }

    // EFFECTS: Displays the starting page
    private void backToMenu() {
        gm.showStartPage();
    }

    @Override
    public void refresh() {
        super.removeStaleComponents();
        setupScoresText();
    }

    @Override
    public void show() {
        gm.showPage(this);
    }
}