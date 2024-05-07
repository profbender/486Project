import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Supplier;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm.Matching;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class OnlineWeightedMatchingWithDelays {

    public static void main(String[] args) {
        final class SkilledPlayer extends Player {
            private double skill;

            public SkilledPlayer(double arrivalTime, double skill) {
                super(arrivalTime);
                this.skill = skill;
            }

            public double matchPerception(Player otherPlayer) {
                if (!(otherPlayer instanceof SkilledPlayer))
                    return Double.POSITIVE_INFINITY;

                return Math.abs(skill - ((SkilledPlayer) otherPlayer).skill);
            }
        }

        final class BasicRandomInstance extends Instance {

            Player lastPlayer = null;
            Random rand = new Random();

            public Player getNextPlayer() {
                if (lastPlayer == null) {
                    lastPlayer = new SkilledPlayer(0.0, rand.nextInt(2000));
                    return lastPlayer;
                } else {
                    lastPlayer = new SkilledPlayer(rand.nextInt(10) + lastPlayer.getArrivalTime(), rand.nextInt(2000));
                    return lastPlayer;
                }
            }
        }

        

        Instance instance = new BasicRandomInstance();

        MatchingAlgorithm algorithm = new Algorithm1();
        Graph<Player, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        double algCost = 0.0;
        

        for (int i = 0; i < 100; i++) {
            ArrayList<Match> matches = algorithm.matchesFromPool();
            Player nextPlayer = instance.getNextPlayer();

            for (Match m : matches) {
                if (m.getStartTime() < nextPlayer.getArrivalTime()) {
                    algorithm.makeMatch(m);
                    algCost += m.matchScore();
                    for (Player p : m.getPlayers())
                        algCost += m.getStartTime() - p.getArrivalTime();

                }
            }

            
            graph.addVertex(nextPlayer);
            for(Player p : graph.vertexSet()){
                if (p == nextPlayer) continue;
                DefaultWeightedEdge e = graph.addEdge(p, nextPlayer);
                graph.setEdgeWeight(e, Math.abs(nextPlayer.getArrivalTime() - p.getArrivalTime()) + p.matchPerception(nextPlayer) + nextPlayer.matchPerception(p));
            }

            algorithm.addPlayer(nextPlayer);
        }

        System.out.println("Alg's cost after 100 players: " + algCost);
        // KolmogorovWeightedPerfectMatching<Player, DefaultWeightedEdge> kmw = new KolmogorovWeightedPerfectMatching<>(graph);
        // Matching<Player, DefaultWeightedEdge> matching = kmw.getMatching();
        // System.out.println("OPT: " + matching.getWeight());

    }

}
