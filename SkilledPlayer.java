public class SkilledPlayer extends Player {
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

            public String toString() {
                return toString(true);
            }

            public String toString(boolean shortId) {
                if (shortId)
                    return super.toString(true);
                else
                    return "(" + super.toString(true) + ": (" + getArrivalTime() + ": " + skill + "))";
            }
        }