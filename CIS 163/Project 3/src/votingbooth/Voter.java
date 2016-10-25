package votingbooth;

import votingbooth.gui.BasicSimulatorController;

/**
 * @author   Roger Ferguson
 */
public class Voter {
    private double timeSpent;
    private VoterStatus status;
    private int voterID;
<<<<<<< HEAD


    /** Controller Object **/
    public BasicSimulatorController basicController;

    // max time person stays in line
    protected double boothTime;

=======
    private Double checkInTime;
>>>>>>> origin/master
    private Double boothTime;
    private int tolerance;

    private Double boothTime;
    private int tolerance;


    public void setID(int voterID) {
        this.voterID = voterID;
    }

    public void addTime(double time) {
        this.timeSpent += time;
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

    public int leaveTime(){
        return basicController.secondsBeforeLeaves;
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
