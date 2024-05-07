import java.util.Random;

public abstract class Instance {

    public abstract Player getNextPlayer();

    public static void main(String[] args){
        final class SkilledPlayer extends Player {
            private double skill;

            public SkilledPlayer(double arrivalTime, double skill){
                super(arrivalTime);
                this.skill = skill;
            }

            public double matchPerception(Player otherPlayer){
                if (!(otherPlayer instanceof SkilledPlayer)) return Double.POSITIVE_INFINITY;

                return Math.abs(skill - ((SkilledPlayer) otherPlayer).skill);
            }
        }
        final class BasicRandomInstance extends Instance {

            Player lastPlayer = null;
            Random rand = new Random();

            public Player getNextPlayer(){
                if (lastPlayer == null){
                    lastPlayer = new SkilledPlayer(0.0, rand.nextInt(2000));
                    return lastPlayer;
                } else {
                    lastPlayer = new SkilledPlayer(rand.nextInt(10) + lastPlayer.getArrivalTime(), rand.nextInt(2000));
                    return lastPlayer;
                }
            }
        }
    }
    
}
