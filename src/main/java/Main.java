import Models.Match;
import Models.Player;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Player> playerListRand = new ArrayList<>();
        List<Player> playerListElo = new ArrayList<>();

        // Makes a list of players with linear skill from 500 to 2500 in steps of 3 skill points
        for (int i = 0; i < 1000; i++) {
            playerListRand.add(new Player(i, 500 + (2 * i)));
            playerListElo.add(new Player(i, 500 + (2 * i), 1200));
        }


        RandomQueue randomQueue = new RandomQueue(playerListRand);
        randomQueue.simulate(2000);

        EloQueue eloQueue = new EloQueue(playerListElo);
        eloQueue.simulate(2000);

        for (Player p: playerListElo) {
            System.out.println(p.getId() + " " + p.getWins() + "-" + p.getLosses() + "-" + p.getDraws() + " Skill: "  + p.getSkill() + " Elo: " + p.getElo());
        }


        LineChart chart = new LineChart(
                "Random Queue Wins / Quality vs skill" ,
                "Wins/Quality Vs Skill", playerListRand, randomQueue.getAllMatches());

        LineChart chart2 = new LineChart(
                "Elo Queue Wins / Quality vs skill" ,
                "Wins/Quality Vs Skill", playerListElo, eloQueue.getAllMatches());

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        chart2.pack();
        RefineryUtilities.centerFrameOnScreen(chart2);
        chart2.setVisible(true);
    }
}
