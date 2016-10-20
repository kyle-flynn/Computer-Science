package votingbooth;

import votingbooth.gui.BasicSimulatorController;

/**
 * Created by Jarred on 10/17/16.
 */
public class SpecialNeedsVoter extends Voter{
    private int tickTime;
    private double timeSpent;
    private Booth Destination;
    private VoterStatus status;
    private int voterID;

    /** Controller Object **/
    public BasicSimulatorController basicController;

    // max time person stays in line
    protected double boothTime;

<<<<<<< HEAD
    public void setID(int voterID) {
        this.voterID = voterID;
    }

    public void addTime(double time) {
        this.timeSpent += time;
    }

    public double getBoothTime() {
        return boothTime;
=======
    public Double getBoothTime() {
        return boothTime * 3;
>>>>>>> origin/master
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

    public int leaveTime(){
        return basicController.secondsBeforeLeaves;
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
