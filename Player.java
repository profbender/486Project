public abstract class Player {

    private double arrivalTime;
    private boolean matched;

    public Player(double arrivalTime){
        this.arrivalTime = arrivalTime;
        this.matched = false;
    }

    public double getArrivalTime(){
        return arrivalTime;
    }

    public boolean matched(){
        return matched;
    }

    public void match(Player otherPlayer){
        matched = true;
    }
    
    public abstract double matchPerception(Player otherPlayer);

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
    }


}
