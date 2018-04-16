import Models.Match;
import Models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WinBasedQueue {

    private List<Player> playerList;
    private List<Match> totalMatchList;
    private Random random;
    private int round;

    public WinBasedQueue(List<Player> list) {
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

    private List<Player> getCopyOfPlayers() {
        return new ArrayList<>(playerList);
    }

    public List<Match> getAllMatches() {
        return totalMatchList;
    }

    private void match() {
        // Keeps track of how many times a match has failed to be found for a player
        int iterations = 1;

        List<Match> matchList = new ArrayList<>();
        List<Player> tempPL = getCopyOfPlayers();

        // Shuffle so less chance of a common opponent many rounds in a row
        Collections.shuffle(tempPL);

        // Since we need 2 players for a game keep searching till 1 or less remain
        while (tempPL.size() > 1) {
            // iterates through players in remainder of pool
            for (int k = 0; k < tempPL.size(); k++) {
                Player p = tempPL.get(k);
                // Iterates through possible matches for a user p
                for (int i = 0; i < tempPL.size(); i++) {
                    if (p.getId() == tempPL.get(i).getId())
                        continue;

                    // see if opponent is suitable
                    if (tempPL.get(i).getWins() >= p.getWins() - iterations && tempPL.get(i).getWins() <= p.getWins() + iterations) {
                        // If opponent is suitable then match them
                        Player p2 = tempPL.get(i);
                        matchList.add(new Match(p, p2, round));
                        // Remove them from the pool of waiting users
                        tempPL.remove(p2);
                        tempPL.remove(p);
                        break;
                    }
                }
            }
            iterations++;
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

}
