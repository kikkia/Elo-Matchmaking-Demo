import Models.Match;
import Models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomQueue {

    private List<Player> playerList;
    private List<Match> totalMatchList;
    private Random random;
    private int round;

    public RandomQueue(List<Player> list) {
        this.playerList = list;
        this.random = new Random();
        this.totalMatchList = new ArrayList<>();
        round = 0;
    }

    public void simulate(int numRounds) {
        for (int i = 0; i < numRounds; i++) {
            round++;
            match();
        }
    }

    public void match() {
        List<Match> matchList = new ArrayList<>();
        List<Player> tempPL = getCopyOfPlayers();

        for (int i = playerList.size()/2; i > 0; i--) {
            Player p2 = tempPL.get(random.nextInt(tempPL.size()));
            while (p2.getId() == tempPL.get(i).getId() && tempPL.size() > 1) {
                p2 = tempPL.get(random.nextInt(tempPL.size()));
            }
            matchList.add(new Match(tempPL.get(i), p2, round));
            // Remove from the queue
            tempPL.remove(i);
            tempPL.remove(p2);
        }
        for (Match m: matchList) {
            int winner = m.getWinner();
            if (winner == 0) {
                m.getPlayer1().draw();
                m.getPlayer2().draw();
            }
            else if (winner == 1) {
                m.getPlayer2().lose();
                m.getPlayer1().win();
            }
            else {
                m.getPlayer1().lose();
                m.getPlayer2().win();
            }
        }
        totalMatchList.addAll(matchList);
    }

    private List<Player> getCopyOfPlayers() {
        return new ArrayList<>(playerList);
    }

    public List<Match> getAllMatches() {
        return totalMatchList;
    }
}
