public abstract class Player {

    private static int numPlayers = 0;

    private double arrivalTime;
    private boolean matched;
    private int index = 0;

    public Player(double arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.matched = false;
        this.index = numPlayers++;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public boolean matched() {
        return matched;
    }

    public void match(Player otherPlayer) {
        matched = true;
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean shortId) {
        if (shortId)
            return "p" + index;
        else
            return "(p" + index + ": " + getArrivalTime() + ")";
    }

    public abstract double matchPerception(Player otherPlayer);

    public static void main(String[] args) {
        @SuppressWarnings("unused")
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
    }

}
