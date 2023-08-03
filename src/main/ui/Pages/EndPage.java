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
            SCORE_TEXT_SIZE = HEIGHT/12,                    SCORE_BOX_HEIGHT = (int) (SCORE_TEXT_SIZE*1.25),
            SCORE_BOX_WIDTH = WIDTH,                        SCORE_Y = HEIGHT/6,

            // High Score
            HIGH_SCORE_TEXT_SIZE = HEIGHT/25,               HIGH_SCORE_BOX_HEIGHT = (int) (HIGH_SCORE_TEXT_SIZE*1.25),
            HIGH_SCORE_Y = HEIGHT - 2* HIGH_SCORE_BOX_HEIGHT - VERT_SPACE,

            // Buttons
            BUTTON_TEXT_SIZE = HEIGHT/25,                   BUTTON_BOX_HEIGHT = (int) (2* BUTTON_TEXT_SIZE*1.25),
            BUTTON_BOX_WIDTH = WIDTH/6,                     BUTTON_Y = HEIGHT/2,
            MENU_BUTTON_X = WIDTH/2 - (int) (BUTTON_BOX_WIDTH*1.5),
            AGAIN_BUTTON_X = WIDTH/2 + BUTTON_BOX_WIDTH/2,

            // Game over message
            MESSAGE_TEXT_SIZE = HEIGHT/25,                  MESSAGE_BOX_HEIGHT = (int) (MESSAGE_TEXT_SIZE*1.25),
            MESSAGE_BOX_WIDTH = WIDTH,
            MESSAGE1_Y = SCORE_Y + SCORE_BOX_HEIGHT + VERT_SPACE,
            MESSAGE2_Y = MESSAGE1_Y + MESSAGE_BOX_HEIGHT + VERT_SPACE
            ;

    // Game over messages
    private final String
            FIRST_MESSAGE_1 = "Now that's a normie number.",
            FIRST_MESSAGE_2 = "Come back once you've trained another 100 years.",

            SECOND_MESSAGE_1 = "Not bad, you've got potential as a degenerate.",
            SECOND_MESSAGE_2 = "Continue your training and play again.",

            THIRD_MESSAGE_1 = "You are a fellow degen. Much respect.",
            THIRD_MESSAGE_2 = "Ultimate victory is within your grasp. Play again!",

            VICTORY_MESSAGE_1 = "Congratulations, you're a master of weird anime ages!",
            VICTORY_MESSAGE_2 = "You have nothing left to prove. You are a champion."
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
        setupText();
        setupBG();
    }

    // EFFECTS: Sets up the background image
    private void setupBG() {
        Image img = gm.getLocalImage(BG_FILE_NAME);
        if (img != null) {
            img = scaleImage(img, WIDTH, HEIGHT);
            bg = (JLabel) makeBG(img);
        } else {
            bg = (JLabel) makeBG(Color.BLACK);
        }

        pane.add(bg, -1);
    }

    private void setupText() {
        setupMessageText();
    }

    private void setupMessageText() {
        String message1;
        String message2;
        int scr = gm.getScore();

        if (scr < 3) {
            message1 = FIRST_MESSAGE_1;
            message2 = FIRST_MESSAGE_2;
        } else if (scr < 7) {
            message1 = SECOND_MESSAGE_1;
            message2 = SECOND_MESSAGE_2;
        } else if (scr < 15) {
            message1 = THIRD_MESSAGE_1;
            message2 = THIRD_MESSAGE_2;
        } else {
            message1 = VICTORY_MESSAGE_1;
            message2 = VICTORY_MESSAGE_2;
        }

        JPanel panel = makePanel();
        JLabel msg1 = (JLabel) makeText(message1, MESSAGE_TEXT_SIZE, Color.WHITE,
                0, MESSAGE1_Y, MESSAGE_BOX_WIDTH, MESSAGE_BOX_HEIGHT);
        JLabel msg2 = (JLabel) makeText(message2, MESSAGE_TEXT_SIZE, Color.WHITE,
                0, MESSAGE2_Y, MESSAGE_BOX_WIDTH, MESSAGE_BOX_HEIGHT);

        panel.add(msg1);
        panel.add(msg2);
        stale_components.add(panel);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
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
        setupMessageText();
    }

    @Override
    public void show() {
        gm.showPage(this);
    }
}