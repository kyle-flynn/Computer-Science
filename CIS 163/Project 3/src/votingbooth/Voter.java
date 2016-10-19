package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Voter {
    private int tickTime;
    private double timeSpent;
    private Booth Destination;
    private VoterStatus status;
    private int voterID;

    // max time person stays in line
    protected double boothTime;

    public void setID(int voterID) {
        this.voterID = voterID;
    }

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

    public int getVoterID() {
        return voterID;
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
