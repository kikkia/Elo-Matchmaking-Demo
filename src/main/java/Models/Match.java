package Models;

import java.util.Random;

public class Match {

    private Random random;
    private Player player1;
    private Player player2;
    private int winner;
    private boolean isDraw;
    private int round;
    private int performanceDiff = 0;

    public Match(Player player1, Player player2, int round) {
        this.player1 = player1;
        this.player2 = player2;
        this.round = round;
        this.winner = 0;
        this.isDraw = false;
        this.random = new Random();
    }

    private void simulateMatch() {
        int player1Performance = player1.getPerformance();
        int player2Performance = player2.getPerformance();
        int p1Tries;
        int p2Tries;

        if (player1Performance < player2Performance) {
            p1Tries = player1.guess(Constants.MATCH_LENGTH);
            p2Tries = player2.guess(getRange(player1Performance, player2Performance));
        }
        else if (player2Performance < player1Performance) {
            p2Tries = player2.guess(Constants.MATCH_LENGTH);
            p1Tries = player1.guess(getRange(player2Performance, player1Performance));
        }
        else {
            p1Tries = player1.guess(Constants.MATCH_LENGTH);
            p2Tries = player2.guess(Constants.MATCH_LENGTH);
        }

        if (p1Tries == p2Tries)
            isDraw = true;
        else if (p1Tries < p2Tries) {
            winner = 1;
        }
        else {
            winner = 2;
        }

    }

    public int getRange(int lower, int upper) {
        performanceDiff = upper - lower;
        return Constants.MATCH_LENGTH - (int) (Constants.MATCH_LENGTH * (1/(1 + Math.pow(Math.E, -(1/300.0) * performanceDiff))));
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getWinner() {
        // if not a draw match has not been simulated
        if (winner == 0 && !isDraw) {
            simulateMatch();
        }
        return winner;
    }

    public double getQaulity() {
        return (1.0 - (1/(1 + Math.pow(Math.E, -(1/300.0) * performanceDiff)))) * 2.0;
    }

    public boolean isDraw() {
        return isDraw;
    }
}
