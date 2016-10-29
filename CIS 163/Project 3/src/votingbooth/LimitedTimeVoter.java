package votingbooth;

/**
 * Created by Jarred on 10/17/16.
 */
public class LimitedTimeVoter extends Voter{
    private double timeSpent;
    private VoterStatus status;
    private int voterID;
    private Double checkInTime;
    private Double boothTime;
    private int tolerance;

    public void setID(int voterID) {
        this.voterID = voterID;
    }

    public void addTime(double time) {
        this.timeSpent += time;
    }

    public Double getBoothTime() {
        return boothTime * 0.5;
    }

    public Double getCheckInTime() {
        return checkInTime;
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

    public void setBoothTime(Double boothTime) {
        this.boothTime = boothTime;
    }

    public void setCheckInTime(Double checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setStatus(VoterStatus status) {
        this.status = status;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public int getTolerance() {
        return tolerance;
    }
}
