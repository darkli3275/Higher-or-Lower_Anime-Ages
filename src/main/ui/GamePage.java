package main.ui;

import main.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static main.persistence.LocalLoader.getLocalImage;
import static main.ui.MainWindow.HEIGHT;
import static main.ui.MainWindow.WIDTH;
import static main.ui.uiComponent.*;

public class GamePage extends Page implements ActionListener {

    private final String checkmarkImgFile = "checkmark.png";
    private final String xMarkImgFile = "x_mark.png";
    private Person p1;
    private Person p2;
    private ArrayList<JPanel> panelsToUpdate;       // keeps references to remove panels after a question is answered
    private boolean correctness;

    private final int vertSpace = HEIGHT/60, horzSpace = WIDTH/50;     // arbitrary small space
    private final int
            // pre-set text & text box sizes
            smallTextSize = HEIGHT/25,                  smallBoxHeight = (int) (smallTextSize*1.25),
            medTextSize = HEIGHT/20,                    medBoxSize = (int) (medTextSize*1.25),
            largeTextSize = HEIGHT/12,                  largeBoxHeight = (int) (largeTextSize*1.25),
            veryLargeTextSize = HEIGHT/10,              veryLargeBoxHeight = (int) (veryLargeTextSize*1.25),
            miscTextSize = (int) (smallTextSize/1.5),   miscBoxSize = (int) (miscTextSize*1.25),

            // text and box sizes for standard screen elements
            backButtonTextSize = miscTextSize,          backButtonHeight = smallBoxHeight,
                    backButtonWidth = WIDTH/15,
            answerTextSize = medTextSize,               answerWidth = WIDTH/6,
                answerHeight = medBoxSize,                  answerX = (int) (WIDTH*0.75 - answerWidth/2),
            ageSize = veryLargeTextSize,                ageBoxHeight = veryLargeBoxHeight,
            seriesSize = smallTextSize,                 seriesBoxHeight = smallBoxHeight,
            nameSize = largeTextSize,                   nameBoxHeight = largeBoxHeight,
            scoreSize = miscTextSize,                   scoreBoxHeight = miscBoxSize,
                scoreY = HEIGHT - 2*smallBoxHeight - vertSpace,

            checkmarkX = WIDTH - WIDTH/4,               checkmarkWidth = WIDTH/12,
                checkmarkHeight = WIDTH/14,             xMarkWidth = WIDTH/12,
                xMarkHeight = WIDTH/12,

            // y-values for standard screen elements
            ageY = HEIGHT/2,
            seriesY = ageY - seriesBoxHeight - vertSpace,
            nameY = seriesY - nameBoxHeight;

    public GamePage(GameManager gm, Person p1, Person p2) {
        super();
        this.gm = gm;
        this.p1 = p1;
        this.p2 = p2;
        correctness = false;
        panelsToUpdate = new ArrayList<>();
        setup();
    }

    @Override
    public void setup() {
        setupButtons();
        setupVS();
        setupScoreText();
        setupP1();
        setupP2();
        setupBG();
    }

    private void setupButtons() {
        JPanel panel = makePanel();

        JButton back = makeButton("Back", backButtonTextSize, e -> backButtonAction(),
                horzSpace, vertSpace, backButtonWidth, backButtonHeight);

        panel.add(back);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    // Default background seen when images don't cover screen
    private void setupBG() {
        bg = new JLabel();
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        bg.setBackground(Color.BLACK);
        bg.setOpaque(true);
        pane.add(bg, -1);
    }

    private void setupP1() {
        setupP1Text();
        setupP1BG();
    }

    private void setupP1BG() {
        JLabel p1BG = (JLabel) makeBG(true, true, false, p1.getImageFile(), 0, 0, WIDTH/2, HEIGHT);
        pane.add(p1BG, JLayeredPane.DEFAULT_LAYER);
    }

    private void setupP1Text() {
        JPanel panel = makePanel();
        int xTopLeft = 0;
        int textBoxWidth = WIDTH/2;
        int yearsOldY = ageY + ageBoxHeight + vertSpace;

        JLabel name = (JLabel) makeText(p1.getName(), nameSize, Color.WHITE,
                xTopLeft, nameY, textBoxWidth, nameBoxHeight);
        JLabel series = (JLabel) makeText("from \"" + p1.getSeries() + "\" is", seriesSize, Color.WHITE,
                xTopLeft, seriesY, textBoxWidth, seriesBoxHeight);
        JLabel age = (JLabel) makeText(Integer.toString(p1.getAge()), ageSize, Color.YELLOW,
                xTopLeft, ageY, textBoxWidth, ageBoxHeight);
        JLabel yearsOld = (JLabel) makeText("years old", smallTextSize, Color.WHITE,
                xTopLeft, yearsOldY, textBoxWidth, smallBoxHeight);

        panel.add(name);
        panel.add(series);
        panel.add(age);
        panel.add(yearsOld);

        pane.add(panel, JLayeredPane.DEFAULT_LAYER);
    }

    private void setupP2() {
        setupP2Buttons();
        setupP2Text();
        setupP2BG();
    }

    private void setupP2Text() {
        JPanel textPanel = makePanel();
        int xTopLeft = WIDTH/2;
        int textBoxWidth = WIDTH/2;
        int thanY = HEIGHT/2 + 2*answerHeight + 2*vertSpace;

        JLabel p2Name = (JLabel) makeText(p2.getName(), largeTextSize, Color.WHITE,
                xTopLeft, nameY, textBoxWidth, largeTextSize);
        JLabel p2From = (JLabel) makeText("from \"" + p2.getSeries() + "\" is", smallTextSize, Color.WHITE,
                xTopLeft, seriesY, textBoxWidth, smallTextSize);
        JLabel p2Than = (JLabel) makeText("than " + p1.getName(), smallTextSize, Color.WHITE,
                xTopLeft, thanY, textBoxWidth, smallTextSize);

        textPanel.add(p2Name);
        textPanel.add(p2From);
        textPanel.add(p2Than);

        pane.add(textPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private void setupP2Buttons() {
        JPanel panel = makePanel();
        int oldHeight = HEIGHT/2;
        int youngHeight = oldHeight + answerHeight + vertSpace;

        JButton older = makeButton("Older", answerTextSize, null, answerX, oldHeight, answerWidth, answerHeight);
        JButton younger = makeButton("Younger", answerTextSize, null, answerX, youngHeight, answerWidth, answerHeight);

        if (p1.getAge() > p2.getAge()) {
            older.addActionListener(e -> incorrectAnswer());
            younger.addActionListener(e -> correctAnswer());
        }
        else if (p1.getAge() < p2.getAge()) {
            older.addActionListener(e -> correctAnswer());
            younger.addActionListener(e -> incorrectAnswer());
        } else {
            older.addActionListener(e -> correctAnswer());
            younger.addActionListener(e -> correctAnswer());
        }

        panel.add(older);
        panel.add(younger);

        panelsToUpdate.add(panel);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void setupP2BG() {
        JLabel p2BG = (JLabel) makeBG(true, true, false, p2.getImageFile(), WIDTH/2, 0, WIDTH/2, HEIGHT);
        pane.add(p2BG, JLayeredPane.DEFAULT_LAYER);
    }

    private void setupScoreText() {
        JPanel panel = makePanel();

        JLabel highScore = (JLabel) makeText("High Score: " + gm.getPlayerData().getHighScore(), scoreSize,
                Color.WHITE, horzSpace, scoreY, WIDTH/2 - horzSpace, smallBoxHeight);
        highScore.setHorizontalAlignment(JLabel.LEFT);

        JLabel score = (JLabel) makeText("Score: " + gm.getPlayerData().getScore(), scoreSize, Color.WHITE,
                WIDTH/2, scoreY, (int) (WIDTH/2 - 1.5*horzSpace), scoreBoxHeight);
        score.setHorizontalAlignment(JLabel.RIGHT);

        panel.add(highScore);
        panel.add(score);

        panelsToUpdate.add(panel);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void setupVS() {
        JPanel panel = makePanel();

        int blackLineWidth = WIDTH/100;
        JLabel blackLine = new JLabel();
        blackLine.setBounds(WIDTH/2 - blackLineWidth/2, 0, blackLineWidth, HEIGHT);
        blackLine.setBackground(Color.BLACK);
        blackLine.setOpaque(true);

        panel.add(blackLine);
        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void correctAnswer() {
        correctness = true;
        gm.correctAnswer();
        removePanelsToUpdate();
        setupScoreText();
        setupAnswerGraphics();
        gm.showPage(pane);

        int waitMillis = 1500;
        Timer timer = new Timer(waitMillis, this);
        timer.setRepeats(false);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (correctness) {
            correctEvent();
        } else {
            incorrectEvent();
        }
    }

    private void correctEvent() {
        p1 = p2;
        p2 = gm.fetchUnseenPerson();
        if (p2 == null) win();
        else {
            pane.removeAll();
            setup();
            gm.showPage(pane);
        }
    }

    private void incorrectEvent() {
        gm.endGame(false);
    }

    private void incorrectAnswer() {
        correctness = false;
        removePanelsToUpdate();
        setupAnswerGraphics();

        int waitMillis = 1500;
        Timer timer = new Timer(waitMillis, this);
        timer.setRepeats(false);
        timer.start();
    }

    private void win() {
        gm.endGame(true);
    }

    private void removePanelsToUpdate() {
        JPanel rip;
        for (JPanel jPanel : panelsToUpdate) {
            rip = jPanel;
            rip.removeAll();
            pane.remove(rip);
        }
    }

    //  correct answer is correct = true
    private void setupAnswerGraphics() {
        JPanel panel = makePanel();
        panel.setLayout(new FlowLayout());
        int panelHeight;

        Image markImg;
        if (correctness) {
            markImg = getLocalImage(checkmarkImgFile).getScaledInstance(checkmarkWidth, checkmarkHeight, Image.SCALE_SMOOTH);
            panelHeight = checkmarkHeight;
        } else {
            markImg = getLocalImage(xMarkImgFile).getScaledInstance(xMarkWidth, xMarkHeight, Image.SCALE_SMOOTH);
            panelHeight = xMarkHeight;
        }
        JLabel mark = new JLabel(new ImageIcon(markImg));

        JLabel p2Age = new JLabel(Integer.toString(p2.getAge()));
        p2Age.setFont(new Font(p2Age.getFont().getFontName(), Font.PLAIN, ageSize));
        p2Age.setForeground(Color.YELLOW);

        panel.add(p2Age);
        panel.add(mark);
        panel.setBounds(WIDTH/2, ageY, WIDTH/2, panelHeight + vertSpace);

        pane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

    private void backButtonAction() {
        gm.resetGame();
        prev.show();
    }

    @Override
    public void show() {
        gm.showPage(pane);
    }
}