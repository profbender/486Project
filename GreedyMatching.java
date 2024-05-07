import java.util.ArrayList;
import java.util.HashSet;

final class GreedyMatching extends MatchingAlgorithm {
    double time = 0;
    ArrayList<Player> playerPool = new ArrayList<>();

    public void addPlayer(Player p) {
        playerPool.add(p);
        time = p.getArrivalTime();
    }

    public void makeMatch(Match m) {
        playerPool.remove(m.getPlayers()[0]);
        playerPool.remove(m.getPlayers()[1]);
        m.getPlayers()[0].match(m.getPlayers()[1]);
        m.getPlayers()[1].match(m.getPlayers()[0]);
    }

    public ArrayList<Match> matchesFromPool() {
        HashSet<Player> matchedOff = new HashSet<>();

        ArrayList<Match> output = new ArrayList<>();

        for (Player p : playerPool) {
            if (matchedOff.contains(p))
                continue;

            for (Player otherPlayer : playerPool) {
                if (p == otherPlayer || matchedOff.contains(otherPlayer))
                    continue;

                Match m = new Match(p, otherPlayer, time);
                if (m.matchScore() < Double.POSITIVE_INFINITY) {
                    output.add(m);
                    matchedOff.add(p);
                    matchedOff.add(otherPlayer);
                    break;
                }
            }
        }

        return output;
    }
}