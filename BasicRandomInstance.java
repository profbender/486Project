import java.util.Random;

public class BasicRandomInstance extends Instance {

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