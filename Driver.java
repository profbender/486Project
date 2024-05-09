import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm.Matching;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Driver {

    public static void main(String[] args) {
        int N = args.length > 0 ? Integer.parseInt(args[0]) : 6;
        boolean print = args.length > 1 ? Boolean.parseBoolean(args[1]) : true;

        Instance instance = new BasicRandomInstance();

        MatchingAlgorithm algorithm = new Algorithm1();

        final class DefaultWeightedEdgeBetter extends DefaultWeightedEdge {

            double weight;

            public DefaultWeightedEdgeBetter(double w) {
                weight = w;
            }

            public String toString() {
                return "{" + getSource() + ", " + getTarget() + ": " + weight + "}";
            }
        }
        Graph<Player, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Player, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);

        double algCost = 0.0;

        ArrayList<Match> algMatches = new ArrayList<>();
        ArrayList<Player> allPlayers = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            ArrayList<Match> matches = algorithm.matchesFromPool();
            Player nextPlayer = instance.getNextPlayer();

            for (Match m : matches) {
                if (m.getStartTime() < nextPlayer.getArrivalTime()) {
                    algorithm.makeMatch(m);
                    algCost += m.matchQuality();
                    for (Player p : m.getPlayers())
                        algCost += m.getStartTime() - p.getArrivalTime();
                    algMatches.add(m);
                }
            }

            graph.addVertex(nextPlayer);
            for (Player p : graph.vertexSet()) {
                if (p == nextPlayer)
                    continue;
                double optScore = Math.abs(nextPlayer.getArrivalTime() - p.getArrivalTime())
                        + p.matchPerception(nextPlayer) + nextPlayer.matchPerception(p);
                DefaultWeightedEdge e = new DefaultWeightedEdgeBetter(optScore);
                graph.addEdge(nextPlayer, p, e);
                graph.setEdgeWeight(e, optScore);
            }

            algorithm.addPlayer(nextPlayer);
            allPlayers.add(nextPlayer);
        }

        ArrayList<Match> finalMatches = algorithm.matchesFromPool();
        for (Match m : finalMatches) {
            algorithm.makeMatch(m);
            algCost += m.matchQuality();
            for (Player p : m.getPlayers())
                algCost += m.getStartTime() - p.getArrivalTime();
            algMatches.add(m);
        }

        if (algMatches.size() != N / 2) {
            System.out.println("ERROR: Didn't match everyone in pool.");
            System.exit(1);
        }

        if (print) {
            System.out.print("The players: \n[");
            for (int i = 0; i < allPlayers.size(); i++) {
                System.out.print(allPlayers.get(i).toString(false));
                if (i < allPlayers.size() - 1)
                    System.out.print(", ");
            }
            System.out.println("]\n");
        }

        System.out.println("Alg's cost after " + N + " players: " + algCost);
        if (print) {
            System.out.print("Alg's Matches: \n[");
            for (int i = 0; i < algMatches.size(); i++) {
                System.out.print(algMatches.get(i).toString(false));
                if (i < algMatches.size() - 1)
                    System.out.print(", ");
            }
            System.out.println("]\n");
        }

        KolmogorovWeightedPerfectMatching<Player, DefaultWeightedEdge> kmw = new KolmogorovWeightedPerfectMatching<>(
                graph);
        Matching<Player, DefaultWeightedEdge> matching = kmw.getMatching();
        System.out.println("OPT: " + matching.getWeight());
        if (print)
            System.out.println(matching.getEdges());
        System.out.println("Ratio: " + (algCost / matching.getWeight()));
        System.out.println(print);
    }
}
