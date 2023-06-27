package main.model;

public class PlayerData {
    private int high_score;
    private int score;
    // could add int array of ID's to represent seen characters

    // Default Constructor, sets score and high_score to 0
    public PlayerData() {
        this.score = 0;
        this.high_score = 0;
    }

    // High Score Constructor, sets score to 0 and high score to high_score
    public PlayerData(int high_score) {
        this.score = 0;
        this.high_score = high_score;
    }

    // EFFECTS: Updates player's score with given score. Updates high score if given score is higher.
    public void updateScore(int score) {
        setScore(score);
        updateHighScore();
    }

    private void updateHighScore() {
        if (score > high_score) {this.high_score = score;}
    }

    private void setScore(int score) {this.score = score;}

    public void setHighScore(int high_score) {this.high_score = high_score;}

    public int getScore() {return this.score;}

    public int getHighScore() {return this.high_score;}
}
