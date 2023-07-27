/**
 * The main page for this game.
 * Consists of two characters' data and pictures + buttons to answer
 * whether the second characters' age is higher or lower than the first.
 *
 * @author Raymond Li
 */

package main.ui.Pages;

import main.model.Person;
import main.ui.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiTools.*;

public class GamePage extends Page implements ActionListener {

    private final String CHECKMARK_FILE_NAME = "checkmark.png";             // local image
    private final String X_MARK_FILE_NAME = "x_mark.png";                   // local image
    private Person p1;
    private Person p2;
    private boolean correctness;       // stores whether player's answer is correct. true = correct

    // Standard size and position values for screen elements
    // Positioning for character data-related elements mostly based on age positioning
    private final int
            P1_X = WIDTH/4,                                 P2_X = (int) (WIDTH*0.75),

            // Arbitrary small space for minor positioning edits
            VERT_SPACE = HEIGHT/60,                         HORZ_SPACE = WIDTH/50,

            // Text and text box sizes
            SMALL_TEXT_SIZE = HEIGHT/25,                    SMALL_BOX_HEIGHT = (int) (SMALL_TEXT_SIZE*1.25),
            MED_TEXT_SIZE = HEIGHT/20,                      MED_BOX_HEIGHT = (int) (MED_TEXT_SIZE*1.25),
            LARGE_TEXT_SIZE = HEIGHT/12,                    LARGE_BOX_HEIGHT = (int) (LARGE_TEXT_SIZE*1.25),
            VERY_LARGE_TEXT_SIZE = HEIGHT/10,               VERY_LARGE_BOX_HEIGHT = (int) (VERY_LARGE_TEXT_SIZE*1.25),
            MISC_TEXT_SIZE = (int) (SMALL_TEXT_SIZE/1.5),   MISC_BOX_HEIGHT = (int) (MISC_TEXT_SIZE*1.25),

            // Back button
            BACK_BUTTON_TEXT_SIZE = MISC_TEXT_SIZE,         BACK_BUTTON_HEIGHT = (int) (MISC_BOX_HEIGHT*1.5),
            BACK_BUTTON_WIDTH = WIDTH/15,

            // Answer - both higher and lower - buttons
            ANSWER_TEXT_SIZE = MED_TEXT_SIZE,               ANSWER_WIDTH = WIDTH/6,
            ANSWER_HEIGHT = MED_BOX_HEIGHT,                 ANSWER_X = P2_X - ANSWER_WIDTH/2,

            // Character data
            AGE_TEXT_SIZE = VERY_LARGE_TEXT_SIZE,               AGE_BOX_HEIGHT = VERY_LARGE_BOX_HEIGHT,
            AGE_Y = HEIGHT/2,

            SERIES_TEXT_SIZE = SMALL_TEXT_SIZE,                 SERIES_BOX_HEIGHT = SMALL_BOX_HEIGHT,
            SERIES_Y = AGE_Y - SERIES_BOX_HEIGHT - VERT_SPACE,

            NAME_TEXT_SIZE = LARGE_TEXT_SIZE,                   NAME_BOX_HEIGHT = LARGE_BOX_HEIGHT,
            NAME_Y = SERIES_Y - NAME_BOX_HEIGHT,

            // Score and High Score
            SCORE_TEXT_SIZE = MISC_TEXT_SIZE,                   SCORE_BOX_HEIGHT = MISC_BOX_HEIGHT,
            SCORE_Y = HEIGHT - 2*SMALL_BOX_HEIGHT - VERT_SPACE,

            // Checkmark
            CHECKMARK_WIDTH = WIDTH/12,                     CHECKMARK_HEIGHT = WIDTH/14,

            // X Mark
            X_MARK_WIDTH = WIDTH/12,                        X_MARK_HEIGHT = WIDTH/12
            ;

    // Creates game page with p1 vs p2 associated with Game Manager gm
    public GamePage(GameManager gm, Person p1, Person p2) {
        super();
        this.gm = gm;
        this.p1 = p1;
        this.p2 = p2;
        correctness = false;
        setupUI();
    }

    // EFFECTS: Sets up the UI elements of the page
    @Override
    public void setupUI() {
        setupStandardButtons();
        setupVS();
        setupScoreText();
        setupP1();
        setupP2();
        setupBG();
    }

    // EFFECTS: Creates default background seen when images don't fully cover screen
    private void setupBG() {
        bg = (JLabel) makeBG(Color.BLACK);
        pane.add(bg, -1);
    }

    // EFFECTS: Sets up page's standard buttons
    private void setupStandardButtons() {
        JPanel panel = makePanel();

        JButton back = makeButton("Back", BACK_BUTTON_TEXT_SIZE, e -> backButtonAction(),
                HORZ_SPACE, VERT_SPACE, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);

        panel.add(back);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up score and high score text
    private void setupScoreText() {
        JPanel panel = makePanel();

        JLabel highScore = (JLabel) makeText("High Score: " + gm.getHighScore(), SCORE_TEXT_SIZE,
                Color.WHITE, HORZ_SPACE, SCORE_Y, WIDTH/2 - HORZ_SPACE, SMALL_BOX_HEIGHT);
        highScore.setHorizontalAlignment(JLabel.LEFT);

        JLabel score = (JLabel) makeText("Score: " + gm.getScore(), SCORE_TEXT_SIZE, Color.WHITE,
                WIDTH/2, SCORE_Y, (int) (WIDTH/2 - 1.5* HORZ_SPACE), SCORE_BOX_HEIGHT);
        score.setHorizontalAlignment(JLabel.RIGHT);

        panel.add(highScore);
        panel.add(score);

        stale_components.add(panel);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up page components between p1 and p2
    private void setupVS() {
        JPanel panel = makePanel();

        int black_line_width = WIDTH/100;
        JLabel blackLine = new JLabel();
        blackLine.setBounds(WIDTH/2 - black_line_width/2, 0, black_line_width, HEIGHT);
        blackLine.setBackground(Color.BLACK);
        blackLine.setOpaque(true);

        panel.add(blackLine);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Sets up page components associated with p1
    private void setupP1() {
        setupP1Text();
        setupP1BG();
    }

    // EFFECTS: Sets up p1's image
    private void setupP1BG() {
        Image pic = gm.getCharacterImage(p1.getID());
        reduceColor(pic);
        JLabel p1BG = (JLabel) makePicture(pic, 0, 0, WIDTH/2, HEIGHT);

        pane.add(p1BG, JLayeredPane.DEFAULT_LAYER);
    }

    // EFFECTS: Sets up p1's text
    private void setupP1Text() {
        JPanel panel = makePanel();
        int x = 0;
        int text_box_width = WIDTH/2;
        int years_old_y = AGE_Y + AGE_BOX_HEIGHT + VERT_SPACE;

        JLabel name = (JLabel) makeText(p1.getName(), NAME_TEXT_SIZE, Color.WHITE,
                x, NAME_Y, text_box_width, NAME_BOX_HEIGHT);
        JLabel series = (JLabel) makeText("from \"" + p1.getSeries() + "\" is", SERIES_TEXT_SIZE, Color.WHITE,
                x, SERIES_Y, text_box_width, SERIES_BOX_HEIGHT);
        JLabel age = (JLabel) makeText(Integer.toString(p1.getAge()), AGE_TEXT_SIZE, Color.YELLOW,
                x, AGE_Y, text_box_width, AGE_BOX_HEIGHT);
        JLabel yearsOld = (JLabel) makeText("years old", SMALL_TEXT_SIZE, Color.WHITE,
                x, years_old_y, text_box_width, SMALL_BOX_HEIGHT);

        panel.add(name);
        panel.add(series);
        panel.add(age);
        panel.add(yearsOld);

        pane.add(panel, JLayeredPane.DEFAULT_LAYER);
    }

    // EFFECTS: Sets up page components associated with p2
    private void setupP2() {
        setupAnswerButtons();
        setupP2Text();
        setupP2BG();
    }

    // EFFECTS: Sets up p2's image
    private void setupP2BG() {
        Image pic = gm.getCharacterImage(p2.getID());
        reduceColor(pic);
        JLabel p2BG = (JLabel) makePicture(pic, WIDTH/2, 0, WIDTH/2, HEIGHT);

        pane.add(p2BG, JLayeredPane.DEFAULT_LAYER);
    }

    // EFFECTS: Sets up p2's text
    private void setupP2Text() {
        JPanel textPanel = makePanel();
        int x = WIDTH/2;
        int text_box_width = WIDTH/2;
        int than_y = HEIGHT/2 + 2*ANSWER_HEIGHT + 2*VERT_SPACE;

        JLabel p2Name = (JLabel) makeText(p2.getName(), LARGE_TEXT_SIZE, Color.WHITE,
                x, NAME_Y, text_box_width, LARGE_TEXT_SIZE);
        JLabel p2From = (JLabel) makeText("from \"" + p2.getSeries() + "\" is", SMALL_TEXT_SIZE, Color.WHITE,
                x, SERIES_Y, text_box_width, SMALL_TEXT_SIZE);
        JLabel p2Than = (JLabel) makeText("than " + p1.getName(), SMALL_TEXT_SIZE, Color.WHITE,
                x, than_y, text_box_width, SMALL_TEXT_SIZE);

        textPanel.add(p2Name);
        textPanel.add(p2From);
        textPanel.add(p2Than);

        pane.add(textPanel, JLayeredPane.DEFAULT_LAYER);
    }

    // EFFECTS: Sets up buttons associated with answering higher or lower questions
    private void setupAnswerButtons() {
        JPanel panel = makePanel();
        int HIGHER_HEIGHT = AGE_Y;
        int LOWER_HEIGHT = HIGHER_HEIGHT + ANSWER_HEIGHT + VERT_SPACE;

        JButton higher = makeButton("Older", ANSWER_TEXT_SIZE, null,
                ANSWER_X, HIGHER_HEIGHT, ANSWER_WIDTH, ANSWER_HEIGHT);
        JButton lower = makeButton("Younger", ANSWER_TEXT_SIZE, null,
                ANSWER_X, LOWER_HEIGHT, ANSWER_WIDTH, ANSWER_HEIGHT);

        if (p1.getAge() > p2.getAge()) {
            higher.addActionListener(e -> incorrectAnswer());
            lower.addActionListener(e -> correctAnswer());
        }
        else if (p1.getAge() < p2.getAge()) {
            higher.addActionListener(e -> correctAnswer());
            lower.addActionListener(e -> incorrectAnswer());
        } else {
            higher.addActionListener(e -> correctAnswer());
            lower.addActionListener(e -> correctAnswer());
        }

        panel.add(higher);
        panel.add(lower);
        stale_components.add(panel);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Saves that the answer was correct, shows correct answer graphics, and continues game after delay
    private void correctAnswer() {
        correctness = true;
        gm.correctAnswer();
        removeStaleComponents();
        setupScoreText();
        setupAnswerGraphics();
        gm.showPage(this);

        delayAndContinue();
    }

    // EFFECTS: Saves that the answer was incorrect, shows incorrect answer graphics, and continues game after delay
    private void incorrectAnswer() {
        correctness = false;
        removeStaleComponents();
        setupAnswerGraphics();

        delayAndContinue();
    }

    // EFFECTS: Sends event to self after delay
    private void delayAndContinue() {
        int waitMillis = 1500;
        Timer timer = new Timer(waitMillis, this);
        timer.setRepeats(false);
        timer.start();
    }

    // EFFECTS: Executes event corresponding to correctness
    public void actionPerformed(ActionEvent e) {
        if (correctness) {
            correctEvent();
        } else {
            incorrectEvent();
        }
    }

    // EFFECTS: Behaviour for proceeding after a correct answer. Remakes the page with p1 = former p2 and new p2
    private void correctEvent() {
        p1 = p2;
        p2 = gm.fetchUnseenPerson();
        if (p2 == null) win();
        else {
            pane.removeAll();
            setupUI();
            gm.showPage(this);
        }
    }

    // EFFECTS: Behaviour for proceeding after an incorrect answer. Ends game with a loss
    private void incorrectEvent() {
        lose();
    }

    // EFFECTS: Tells game manager to end game with a win
    private void win() {
        gm.endGame(true);
    }

    // EFFECTS: Tells game manager to end game with a loss
    private void lose() {
        gm.endGame(false);
    }

    // EFFECTS: Sets up graphics corresponding to whether an answer was correct or incorrect
    private void setupAnswerGraphics() {
        JPanel panel = makePanel();
        panel.setLayout(new FlowLayout());

        Image markImg;
        if (correctness) {
            markImg = gm.getLocalImage(CHECKMARK_FILE_NAME);
            markImg = scaleImage(markImg, CHECKMARK_WIDTH, CHECKMARK_HEIGHT);
        } else {
            markImg = gm.getLocalImage(X_MARK_FILE_NAME);
            markImg = scaleImage(markImg, X_MARK_WIDTH, X_MARK_HEIGHT);
        }

        JLabel mark = new JLabel(new ImageIcon(markImg));
        JLabel p2Age = (JLabel) makeText(Integer.toString(p2.getAge()), AGE_TEXT_SIZE, Color.YELLOW);

        panel.add(p2Age);
        panel.add(mark);
        panel.setBounds(WIDTH/2, AGE_Y, WIDTH/2, AGE_BOX_HEIGHT + VERT_SPACE);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // EFFECTS: Resets game state and goes to previous screen
    private void backButtonAction() {
        gm.resetGame();
        prev.show();
    }

    // EFFECTS: Displays this page on screen
    @Override
    public void show() {
        gm.showPage(this);
    }
}