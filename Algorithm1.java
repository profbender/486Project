import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Algorithm1 extends MatchingAlgorithm {

    double time = 0;
    ArrayList<Player> pool = new ArrayList<>();
    HashMap<Player, Double> countDown = new HashMap<>();
    HashMap<Player, Match> countDownMatch = new HashMap<>();
    PriorityQueue<Match> pQueue = new PriorityQueue<>(new Comparator<Match>() {
        public int compare(Match m, Match n) {
            return Double.compare(m.getStartTime(), n.getStartTime());
        }
    });

    @Override
    public void addPlayer(Player p) {

        double minCost = Double.POSITIVE_INFINITY;
        Match bestMatch = null;
        for (Player otherPlayer : pool) {
            Match tempMatch = new Match(p, otherPlayer, p.getArrivalTime());
            double optEstimate = tempMatch.matchScore();
            if (optEstimate < minCost) {
                minCost = optEstimate;
                bestMatch = tempMatch;
            }
        }

        if (bestMatch != null) {
            countDown.put(p, minCost);
            countDownMatch.put(p, bestMatch);
            pQueue.offer(new Match(p, bestMatch.otherPlayer(p), p.getArrivalTime() + bestMatch.matchScore()));
        }

        pool.add(p);
        time = p.getArrivalTime();
    }

    @Override
    public void makeMatch(Match m) {
        for (Player p : m.getPlayers()) {
            pool.remove(p);
            countDown.remove(p);
            countDownMatch.remove(p);
        }
    }

    @Override
    public ArrayList<Match> matchesFromPool() {
        HashSet<Player> matchedOff = new HashSet<>();
        ArrayList<Match> output = new ArrayList<>();
        while (pQueue.size() > 0) {
            Match m = pQueue.poll();

            boolean bothAvailable = true;
            for (Player p : m.getPlayers())
                if (matchedOff.contains(p))
                    bothAvailable = false;

            if (bothAvailable) {

                for (Player p : m.getPlayers())
                    matchedOff.add(p);

                output.add(m);
            } else {
                for (Player p : m.getPlayers()) {
                    if (!matchedOff.contains(p)) {
                        double minCost = Double.POSITIVE_INFINITY;
                        Match bestMatch = null;
                        for (Player otherPlayer : pool) {
                            if (matchedOff.contains(otherPlayer))
                                continue;

                            Match tempMatch = new Match(p, otherPlayer, p.getArrivalTime());
                            double optEstimate = tempMatch.matchScore();
                            if (optEstimate < minCost) {
                                minCost = optEstimate;
                                bestMatch = tempMatch;
                            }
                        }

                        pQueue.offer(
                                new Match(p, bestMatch.otherPlayer(p), bestMatch.matchScore() + p.getArrivalTime()));
                    }
                }
            }
        }
        return output;
    }

}