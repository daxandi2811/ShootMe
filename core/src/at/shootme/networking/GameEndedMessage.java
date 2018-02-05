package at.shootme.networking;

/**
 * Created by Nicole on 18.06.2017.
 */
public class GameEndedMessage {

    private String winningPlayerEntityId;
    private int winningScore;
    private int winningRemainingLives;

    /**
     * returns the player who won the round
     * @return
     */
    public String getWinningPlayerEntityId() {
        return winningPlayerEntityId;
    }

    /**
     * sets the id of the winning player
     * @param winningPlayerEntityId
     */
    public void setWinningPlayerEntityId(String winningPlayerEntityId) {
        this.winningPlayerEntityId = winningPlayerEntityId;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }

    public int getWinningRemainingLives() {
        return winningRemainingLives;
    }

    public void setWinningRemainingLives(int winningRemainingLives) {
        this.winningRemainingLives = winningRemainingLives;
    }
}
