/**
 * Common properties between pages in the game.
 *
 * @author Raymond Li
 */

package main.ui.Pages;

import main.ui.GameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Page {

    protected JLayeredPane pane;
    protected JLabel bg;
    protected Page next;
    protected Page prev;
    protected GameManager gm;
    protected ArrayList<Component> stale_components;

    public Page() {
        pane = new JLayeredPane();
        next = null;
        prev = null;
        stale_components = new ArrayList<>();
    }

    public Page(GameManager gm) {
        pane = new JLayeredPane();
        next = null;
        prev = null;
        this.gm = gm;
        stale_components = new ArrayList<>();
    }

    // Setters
    public void setNextPage(Page pg) {
        next = pg;
    }
    public void setPrevPage(Page pg) {
        prev = pg;
    }
    public void setGameManager(GameManager gm) {this.gm = gm;}

    // Getters
    public GameManager getGameManager() {return gm;}
    public Page getNextPage() {return next;}
    public Page getPrevPage() {return prev;}
    public JLayeredPane getPane() {return pane;}
    public ArrayList<Component> getStaleComponents() {return stale_components;}

    // EFFECTS: Refreshes any stale page components
    public void removeStaleComponents() {
        for (Component component : stale_components) {
            pane.remove(component);
        }
    }

    // EFFECTS: Refreshes any stale page components
    public void refresh() {
        removeStaleComponents();
    }

    // EFFECTS: Displays page on screen
    abstract void show();

    // EFFECTS: Sets up the UI elements of the page
    abstract void setupUI();
}