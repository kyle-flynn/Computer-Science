package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Voter {
    private int tickTime;
    private Booth Destination;

    // max time person stays in line
    protected double boothTime;

    public double getBoothTime() {
        return boothTime;
    }

    public Booth getDestination() {
        return Destination;
    }

    public int getTickTime() {
        return tickTime;
    }

    public void setDestination(Booth destination) {
        Destination = destination;
    }

    public void setTickTime(int tickTime) {
        this.tickTime = tickTime;
    }

    public void setBoothTime(double checkInTime) {
        this.boothTime = checkInTime;
    }
}
