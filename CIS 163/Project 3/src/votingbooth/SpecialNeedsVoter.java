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

public class SpecialNeedsVoter extends Voter {
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

    public void setID(int voterID) {
        this.voterID = voterID;
    }

    public void addTime(double time) {
        this.timeSpent += time;
    }

    public double getBoothTime() {
        return boothTime;
    }
    public Double getBoothTime() {
        return boothTime * 3.0;
    }

    public Double getCheckInTime() {
        return checkInTime * 1.5;
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

    public void setBoothTime(Double boothTime) {
        this.boothTime = boothTime;

    }

    public void setCheckInTime(Double checkInTime) {
        this.checkInTime = checkInTime;
    }


    public void setBoothTime(double checkInTime) {
        this.boothTime = checkInTime;
    }


    public void setStatus(VoterStatus status) {
        this.status = status;
    }


    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public int getTolerance() {
        return tolerance * 2;
    }

}
