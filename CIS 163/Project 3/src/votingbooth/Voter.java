package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Voter {
    private double timeSpent;
    private VoterStatus status;
    private int voterID;
    protected Double checkInTime;
    protected Double boothTime;
    protected int tolerance;
    private boolean isPissed;
    private boolean hasVoted;
    private boolean hasCheckedIn;

    public void setID(int voterID) {
        this.voterID = voterID;
    }

    public void addTime(double time) {
        this.timeSpent += time;
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

    public void setPissed(boolean isPissed) {
        this.isPissed = isPissed;
    }

    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public void setCheckedIn(boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public boolean isPissed() {
        return isPissed;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public boolean hasCheckedIn() {
        return hasCheckedIn;
    }

    public int getTolerance() {
        return tolerance;
    }

    public Double getBoothTime() {
        return boothTime;
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

}
