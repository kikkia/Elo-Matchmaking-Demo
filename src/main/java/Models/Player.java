package Models;

import java.util.Random;

public class Player {
    private int id;
    private int skill;
    private int elo;
    private Random random;
    private final double consistancy = 0.05;

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    public Player(int id, int skill) {
        this.id = id;
        this.skill = skill;
        this.random = new Random();
    }

    public Player(int id, int skill, int elo) {
        this.id = id;
        this.skill = skill;
        this.elo = elo;
        this.random = new Random();
    }

    public int getPerformance() {
        int min = (int) Math.floor(skill - (skill * consistancy));
        int max = (int) Math.floor(skill + (skill * consistancy));

        return random.nextInt((max - min) + 1) + min;
    }

    public int guess(int range) {
        int numToGuess = random.nextInt(range);
        int guessedNum = -1;
        int tries = 0;
        while (numToGuess != guessedNum) {
            guessedNum = random.nextInt(range);
            tries++;
        }
        return tries;
    }

    public int setNewRating(int opponentRating, double gameResult) {
        if (gameResult == Constants.GAME_LOSS) {
            losses++;
        } else if (gameResult == Constants.GAME_WON) {
            wins++;
        } else if (gameResult == Constants.GAME_DRAW) {
            draws++;
        }

        double kFactor = (elo > 2500) ? 15.0 : 20.0;
        double expectedScore = getExpectedScore(opponentRating);

        return calculateNewRating(gameResult, expectedScore, kFactor);
    }

    private int calculateNewRating(double gameResult, double expectedScore, double kFactor) {
        int newRating = elo + (int) (kFactor * (gameResult - expectedScore));
        this.elo = newRating;
        return newRating;
    }

    private double getExpectedScore(int opponentRating) {
        return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - elo) / 400.0)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public double getConsistancy() {
        return consistancy;
    }

    public void win() {
        wins++;
    }

    public void lose() {
        losses++;
    }

    public void draw() {
        draws++;
    }

    protected void setWins(int wins) {
        this.wins = wins;
    }

    protected void setLosses(int losses) {
        this.losses = losses;
    }

    protected void setDraws(int draws) {
        this.draws = draws;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public Player clone() {
        Player p = new Player(this.id, this.skill, this.elo);
        p.setWins(wins);
        p.setLosses(losses);
        p.setDraws(draws);

        return p;
    }
}
