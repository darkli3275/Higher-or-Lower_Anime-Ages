package main.ui;

import main.model.Person;
import main.model.PlayerData;
import main.persistence.NetLoader;
import main.persistence.Saver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.persistence.LocalLoader.initializePlayerData;
import static main.persistence.NetLoader.initializeCharacterData;
import static main.persistence.Saver.saveScore;

public class GameManager {
    private static JFrame window;
    private PlayerData playerData;
    private ArrayList<Page> pages;          // [0] = StartPage, [1] = GamePage, [2] = EndPage
    private ArrayList<Integer> seenIDs;     // list of Person ID's already used in the game

    private StartPage startPage;
    private GamePage gamePage;
    private EndPage endPage;

    public GameManager(JFrame frame) {
        window = frame;
        playerData = initializePlayerData();
        seenIDs = new ArrayList<>();
        initializeCharacterData();

        pagesSetup();
        // show StartPage
        startPage.show();
    }
    
    private void pagesSetup() {
        pages = new ArrayList<>();
        startPage = new StartPage(this);
        gamePage = null;
        endPage = new EndPage(this);

        // page navigation setup
        endPage.setNextPage(startPage);

        pages.add(startPage);
        pages.add(gamePage);
        pages.add(endPage);
        // pages = {StartPage, GamePage, EndPage}
    }

    protected void correctAnswer() {
        playerData.addScore(1);
    }

    protected void incorrectAnswer() {
        // do nothing for now
    }

    // REQUIRES: at least 2 non-zero ID Person's exist in CharacterData.json
    protected void newGame() {
        playerData.setScore(0);
        seenIDs.clear();
        gamePage = new GamePage(this, fetchUnseenPerson(), fetchUnseenPerson());
        gamePage.setNextPage(endPage);
        gamePage.setPrevPage(startPage);
        showPage(gamePage.getPane());
    }

    protected void resetGame() {
        seenIDs.clear();
    }

    protected void endGame(boolean winStatus) {
        Saver.saveScore(playerData);
        endPage.gameOver();
        endPage.show();
        //showPage(endPage.getPane());
    }

    protected Person fetchUnseenPerson() {
        Person p = NetLoader.getRandomPerson(seenIDs);
        if (p == null) return null;
        seenIDs.add(p.getID());
        return p;
    }

    protected void saveAndQuit() {
        saveScore(playerData);
        System.exit(playerData.getScore());
    }

    protected void showPage(Container p) {
        SwingUtilities.invokeLater(() -> {
            window.setContentPane(p);
            window.repaint();
            window.setVisible(true);
        });
    }

    public PlayerData getPlayerData() {return playerData;}
}