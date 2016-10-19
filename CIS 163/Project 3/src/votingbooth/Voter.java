package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Voter {
    private int tickTime;
    private double timeSpent;
    private Booth Destination;
    private VoterStatus status;

    // max time person stays in line
    protected double boothTime;

    public void addTime(double time) {
        this.timeSpent += time;
    }

    public double getBoothTime() {
        return boothTime;
    }

    public Booth getDestination() {
        return Destination;
    }

    public int getTickTime() {
        return tickTime;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public VoterStatus getStatus() {
        return status;
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

    public void setStatus(VoterStatus status) {
        this.status = status;
    }

}
