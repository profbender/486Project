public class Match {

    private Player p1, p2;
    private double startTime;

    public Match(Player p1, Player p2, double startTime) {
        this.p1 = p1;
        this.p2 = p2;
        this.startTime = startTime;
    }

    public Player[] getPlayers() {
        return new Player[] { p1, p2 };
    }

    public Player otherPlayer(Player p) {
        if (p1 == p)
            return p2;
        if (p2 == p)
            return p1;
        return null;
    }

    public double getStartTime() {
        return startTime;
    }

    public double matchQuality() {
        return p1.matchPerception(p2) + p2.matchPerception(p1);
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean shortForm) {
        if (shortForm)
            return "{" + p1.toString(true) + " : " + p2.toString(true) + "}";
        else
            return "{" + p1.toString(true) + ", " + p2.toString(true) + ": " + getStartTime() + " : " + matchQuality()
                    + "}";
    }
}
