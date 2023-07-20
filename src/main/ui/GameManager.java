package main.ui;

import main.model.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.persistence.LocalLoader.initializePlayerData;
import static main.persistence.NetLoader.initializeCharacterData;
import static main.persistence.Saver.saveScore;

public class GameManager {
    private static JFrame window;
    protected static PlayerData playerData;
    private ArrayList<Page> pages;          // [0] = StartPage, [1] = GamePage, [2] = EndPage

    public GameManager(JFrame frame) {
        window = frame;
        playerData = initializePlayerData();
        initializeCharacterData();

        pagesSetup();
        // show StartPage
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setContentPane(pages.get(0).getPane());
                window.setVisible(true);
            }
        });
    }
    
    private void pagesSetup() {
        pages = new ArrayList<>();
        StartPage startPage = new StartPage();
        GamePage gamePage = new GamePage();
        EndPage endPage = new EndPage();

        // page navigation setup
        startPage.setNextPage(gamePage);
        gamePage.setNextPage(endPage);
        gamePage.setPrevPage(startPage);
        endPage.setNextPage(startPage);

        pages.add(startPage);
        pages.add(gamePage);
        pages.add(endPage);
        // pages = {StartPage, GamePage, EndPage}

    }

    protected static void saveAndQuit() {
        saveScore(playerData);
        System.exit(playerData.getScore());
    }

    protected static void showPage(Container p) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setContentPane(p);
                window.setVisible(true);
            }
        });
    }
}