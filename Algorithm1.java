import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Algorithm1 extends MatchingAlgorithm {

    double time = 0;
    HashSet<Player> pool = new HashSet<>();
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
            double optEstimate = tempMatch.matchQuality();
            if (optEstimate < minCost) {
                minCost = optEstimate;
                bestMatch = tempMatch;
            }
        }

        if (bestMatch != null)
            pQueue.offer(new Match(p, bestMatch.otherPlayer(p), p.getArrivalTime() + bestMatch.matchQuality()));

        pool.add(p);
        time = p.getArrivalTime();
    }

    @Override
    public void makeMatch(Match m) {
        for (Player p : m.getPlayers()) {
            p.match(m.otherPlayer(p));
            pool.remove(p);
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
                if (matchedOff.contains(p) || !pool.contains(p))
                    bothAvailable = false;

            if (bothAvailable) {

                for (Player p : m.getPlayers())
                    matchedOff.add(p);

                output.add(m);
            } else {
                for (Player p : m.getPlayers()) {
                    if (!matchedOff.contains(p) && pool.contains(p)) {
                        double minCost = Double.POSITIVE_INFINITY;
                        Match bestMatch = null;
                        for (Player otherPlayer : pool) {
                            if (otherPlayer == p || matchedOff.contains(otherPlayer))
                                continue;

                            Match tempMatch = new Match(p, otherPlayer, p.getArrivalTime());
                            double optEstimate = tempMatch.matchQuality();
                            if (optEstimate < minCost) {
                                minCost = optEstimate;
                                bestMatch = tempMatch;
                            }
                        }
                        if (bestMatch != null)
                            pQueue.offer(
                                    new Match(p, bestMatch.otherPlayer(p),
                                            bestMatch.matchQuality() + p.getArrivalTime()));
                    }
                }
            }
        }
        for (Match m : output)
            pQueue.offer(m);
        return output;
    }

}