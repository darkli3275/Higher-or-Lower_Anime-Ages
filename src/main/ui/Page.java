package main.ui;

import javax.swing.*;
import java.awt.*;

abstract class Page {

    protected JLayeredPane pane;
    protected JLabel bg;
    protected Page next;
    protected Page prev;
    protected GameManager gm;

    public Page() {
        pane = new JLayeredPane();
        next = null;
        prev = null;
    }

    protected void setNextPage(Page pg) {
        next = pg;
    }

    protected void setPrevPage(Page pg) {
        prev = pg;
    }

    protected void setGameManager(GameManager gm) {this.gm = gm;}

    protected Page getNextPage() {
        return next;
    }

    protected Page getPrevPage() {
        return prev;
    }

    protected Container getPane() {return pane;}

    abstract void show();

    // EFFECTS: sets up the UI elements of the page
    abstract void setup();
}