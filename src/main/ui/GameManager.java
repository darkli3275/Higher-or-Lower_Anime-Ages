/**
 * This class creates the pages needed for the game and manages system behaviours:
 * retrieving and saving data, modifying game data, modifying and showing pages, losing, winning.
 *
 * @author Raymond Li
 */

package main.ui;

import main.model.Person;
import main.model.PlayerData;
import main.persistence.LocalLoader;
import main.persistence.NetLoader;
import main.persistence.Saver;
import main.ui.Pages.EndPage;
import main.ui.Pages.GamePage;
import main.ui.Pages.Page;
import main.ui.Pages.StartPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.persistence.LocalLoader.initializePlayerData;
import static main.persistence.NetLoader.initializeCharacterData;
import static main.persistence.Saver.saveScore;

public class GameManager {
    private static JFrame window;
    private PlayerData player_data;
    private ArrayList<Integer> seen_IDs;     // list of Person ID's already used

    private StartPage start_page;
    private GamePage gamePage;
    private EndPage endPage;

    // EFFECTS: Creates GameManager that manages games' pages and behaviours
    public GameManager(JFrame frame) {
        window = frame;
        player_data = initializePlayerData();
        if (player_data == null) player_data = new PlayerData();
        seen_IDs = new ArrayList<>();
        setup();
    }

    // EFFECTS: Loads character data and sets up pages
    private void setup() {
        initializeCharacterData();
        pagesSetup();
    }

    // EFFECTS: Sets up game's pages
    private void pagesSetup() {
        start_page = new StartPage(this);
        gamePage = null;
        endPage = new EndPage(this);
    }

    // EFFECTS: Refreshes starting page's stale components and displays it on screen
    public void showStartPage() {
        start_page.refresh();
        start_page.show();
    }

    // EFFECTS: Shows p on GUI
    public void showPage(Page p) {
        SwingUtilities.invokeLater(() -> {
            window.setContentPane(p.getPane());
            window.repaint();
            window.setVisible(true);
        });
    }

    // REQUIRES: initializeCharacterData() was called beforehand
    //           at least 2 non-zero ID Person's exist in NetLoader.person_mp
    // EFFECTS:  Creates and starts a new game with score 0
    public void newGame() {
        player_data.setScore(0);
        seen_IDs.clear();

        Person p1 = fetchUnseenPerson();
        Person p2 = fetchUnseenPerson();
        if (p1 == null || p2 == null) {endGame(false);}
        gamePage = new GamePage(this, p1, p2);
        gamePage.setNextPage(endPage);
        gamePage.setPrevPage(start_page);

        showPage(gamePage);
    }

    // EFFECTS: Resets accumulated game information and score
    public void resetGame() {
        seen_IDs.clear();
        player_data.setScore(0);
    }

    // EFFECTS: Ends the game. winStatus = true indicates a win
    public void endGame(boolean winStatus) {
        Saver.saveScore(player_data);
        endPage.refresh();
        endPage.show();
    }

    // EFFECTS: Adds 1 to score
    public void correctAnswer() {
        player_data.addScore(1);
    }

    // EFFECTS: Ends the game
    public void incorrectAnswer() {
        endGame(false);
    }

    // EFFECTS: Returns a random Person from NetLoader excluding ID's already seen. Returns null if fails
    public Person fetchUnseenPerson() {
        Person p = NetLoader.getRandomPerson(seen_IDs);
        if (p == null) return null;
        seen_IDs.add(p.getID());
        return p;
    }

    // EFFECTS: Returns image stored locally. Returns null if fail
    public Image getLocalImage(String file_name) {
        Image img = LocalLoader.getLocalImage(file_name);
        if (img == null) {System.exit(1);}
        return img;
    }

    // EFFECTS: Returns character associated with id's image from NetLoader's simulated online database
    public Image getCharacterImage(int id) {
        Image img = NetLoader.getNetCharacterImage(id);
        if (img == null) {System.exit(2);}
        return img;
    }

    // EFFECTS: Returns player's score
    public int getScore() {return player_data.getScore();}

    // EFFECTS: Returns player's high score
    public int getHighScore() {return player_data.getHighScore();}

    // EFFECTS: Saves player data and quits the game
    public void saveAndQuit() {
        saveScore(player_data);
        System.exit(0);
    }

    public PlayerData getPlayerData() {return player_data;}
    public void setPlayerData(PlayerData pd){player_data = pd;}
}

/* Unused Pages list code
    private ArrayList<Page> pages;
    pages = new ArrayList<>();
    pages.add(startPage);
    pages.add(gamePage);
    pages.add(endPage);
*/