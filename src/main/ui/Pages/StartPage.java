/**
 * Page displayed when game starts.
 * Consists of background image, title, high score, exit button, and button that starts the game.
 *
 * @author Raymond LI
 */

package main.ui.Pages;

import main.ui.GameManager;

import javax.swing.*;
import java.awt.*;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiTools.*;

public class StartPage extends Page {

    private final String BG_FILE_NAME = "startPageBG.png";      // local image

    // Standard size and position values for screen elements.
    // Positioning for this page is based mostly around the title's.
    private final int
            // Arbitrary small space for minor positioning edits
            VERT_SPACE = HEIGHT/60,                         HORZ_SPACE = WIDTH/50,

            // Text and text box sizes
            BIG_TEXT_SIZE = HEIGHT/10,                      BIG_BOX_HEIGHT = (int) (BIG_TEXT_SIZE*1.25),
            MED_TEXT_SIZE = HEIGHT/20,                      MED_BOX_HEIGHT = (int) (MED_TEXT_SIZE*1.25),

            // Title
            TITLE_TEXT_SIZE = BIG_TEXT_SIZE,                TITLE_BOX_HEIGHT = BIG_BOX_HEIGHT,
                TITLE_Y = HEIGHT/2 - TITLE_BOX_HEIGHT - VERT_SPACE,

            // Button
            BUTTON_TEXT_SIZE = MED_TEXT_SIZE,               BUTTON_WIDTH = WIDTH/6,
            BUTTON_HEIGHT = (int) (MED_BOX_HEIGHT*1.5),     BUTTON_X = WIDTH/2 - BUTTON_WIDTH/2
            ;

    // Creates starting page with gm as Game Manager
    public StartPage(GameManager gm) {
        super();
        this.gm = gm;
        setupUI();
    }

    // EFFECTS: Sets up the UI elements of the page
    @Override
    public void setupUI() {
        setupButtons();
        setupStaticText();
        setupDynamicComponents();
        setupBG();
    }

    // EFFECTS: Sets up page's background
    private void setupBG() {
        Image bgImg = gm.getLocalImage(BG_FILE_NAME);
        bg = (JLabel) makeBG(bgImg);

        pane.add(bg, -1);
    }

    // EFFECTS: Sets up page text that won't need refreshing
    private void setupStaticText() {
        JPanel panel = makePanel();

        JLabel titleText = (JLabel) makeText("Higher or Lower? Anime Ages!", TITLE_TEXT_SIZE, Color.WHITE,
                0, TITLE_Y, WIDTH, TITLE_BOX_HEIGHT);

        panel.add(titleText);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up page buttons
    private void setupButtons() {
        JPanel panel = makePanel();
        int START_BUTTON_Y = TITLE_Y + TITLE_BOX_HEIGHT + 3*VERT_SPACE;
        int EXIT_BUTTON_Y = START_BUTTON_Y + BUTTON_HEIGHT + 2*VERT_SPACE;

        JButton startButton = makeButton("Start!", BUTTON_TEXT_SIZE, e -> startButtonAction(),
                BUTTON_X, START_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        JButton exitButton = makeButton("Exit", BUTTON_TEXT_SIZE, e -> gm.saveAndQuit(),
                BUTTON_X, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        panel.add(startButton);
        panel.add(exitButton);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up page's dynamic components that replace stale ones
    private void setupDynamicComponents() {
        setupDynamicText();
    }

    // EFFECTS: Sets up page text that needs to be refreshed
    private void setupDynamicText() {
        setupScoresText();
    }

    // EFFECTS: Sets up score and high score text
    private void setupScoresText() {
        JPanel panel = makePanel();
        JLabel highScoreText = (JLabel) makeText("High Score: " + gm.getHighScore(),
                MED_TEXT_SIZE, Color.WHITE, 0, HEIGHT - 2*MED_BOX_HEIGHT, WIDTH, MED_BOX_HEIGHT);

        panel.add(highScoreText);
        stale_components.add(panel);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Starts a new game
    private void startButtonAction() {
        gm.newGame();
    }

    // EFFECTS: Refreshes any stale page components
    @Override
    public void refresh() {
        super.removeStaleComponents();
        setupDynamicComponents();
    }

    // EFFECTS: Displays this page on screen
    @Override
    public void show() {
        gm.showPage(this);
    }
}
