/**
 * Represents information associated with the player: score, high score, previous score, total score.
 * total_score and prev_score are currently unused.
 *
 * @author Raymond Li
 */

package main.model;

import org.json.simple.JSONObject;

public class PlayerData {

    private int total_score;            // lifetime accumulated score of player
    private int prev_score;             // score achieved the previous game session
    private int high_score;
    private int score;

    // Default Constructor. sets score and high_score to 0
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

    // EFFECT: Returns JSON representation of player data
    public JSONObject playerDataToJSON() {
        JSONObject jo = new JSONObject();
        jo.put("prev_score", prev_score);
        jo.put("high_score", high_score);
        jo.put("total_score", total_score);

        return jo;
    }

    // EFFECTS: Add i to score and total score
    public void addScore(int i) {
        updateScore(score + i);
        total_score += i;
    }

    // EFFECTS: Updates player's score with given score. Updates high score.
    public void updateScore(int score) {
        setScore(score);
        updateHighScore();
    }

    // EFFECTS: Updates high score if current score is higher.
    private void updateHighScore() {
        if (score > high_score) {setHighScore(score);}
    }

    // Getters
    public int getScore() {return this.score;}
    public int getPrevScore() {return this.prev_score;}
    public int getTotalScore() {return this.total_score;}
    public int getHighScore() {return this.high_score;}

    // Setters
    public void setScore(int score) {this.score = score;}
    public void setHighScore(int high_score) {this.high_score = high_score;}
    public void setTotalScore(int total_score) {this.total_score = total_score;}
    public void setPrevScore(int prev_score) {this.prev_score = prev_score;}
}
