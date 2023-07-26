package main.model;

import org.json.simple.JSONObject;

public class PlayerData {
    private int total_score;
    private int prev_score;
    private int high_score;
    private int score;
    // could add int array of ID's to represent seen characters

    // Default Constructor, sets score and high_score to 0
    public PlayerData() {
        this.score = 0;
        this.high_score = 0;
        this.prev_score = 0;
        this.total_score = 0;
    }

    // Creates player with current score = 0
    public PlayerData(int prev_score, int high_score, int total_score) {
        this.score = 0;
        this.high_score = high_score;
        this.prev_score = prev_score;
        this.total_score = total_score;
    }

    public JSONObject playerDataToJSON() {
        JSONObject jo = new JSONObject();
        jo.put("prev_score", prev_score);
        jo.put("high_score", high_score);
        jo.put("total_score", total_score);

        return jo;
    }

    // EFFECTS: add i to score and total score
    public void addScore(int i) {
        updateScore(score + i);
        total_score += i;
    }

    // EFFECTS: Updates player's score with given score. Updates high score if given score is higher.
    public void updateScore(int score) {
        setScore(score);
        updateHighScore();
    }

    private void updateHighScore() {
        if (score > high_score) {setHighScore(score);}
    }

    public void setScore(int score) {this.score = score;}

    public void setHighScore(int high_score) {this.high_score = high_score;}

    public void setTotalScore(int total_score) {this.total_score = total_score;}

    public void setPrevScore(int prev_score) {this.prev_score = prev_score;}

    public int getScore() {return this.score;}

    public int getPrevScore() {return this.prev_score;}

    public int getTotalScore() {return this.total_score;}

    public int getHighScore() {return this.high_score;}
}
