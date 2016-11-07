package votingbooth;

/*****************************************************************
Voter class. This class is designed to be Overriden and extended
multiple times.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class Voter {

    /** Contains the time at which the voter checks in. This is
    protected so that other classes may overwrite this variable. */
    protected Double checkInTime;

    /** Contains the time at which the voter has voted. This is
    protected so that other classes may overwrite this variable. */
    protected Double boothTime;

    /** Contains the time required before a voter leaves. This is
    protected so that other classes may overwrite this variable. */
    protected int tolerance;

    /** Contains the total time spend in the simulation. */
    private double timeSpent;

    /** Contains the current status of the voter in the simulation. */
    private VoterStatus status;

    /** Contains the identification number of the voter. */
    private int voterID;

    /** Determines whether or note the voter is mad and leaves the sim. */
    private boolean isPissed;

    /** Determines whether or not the voter has voted in the simulation. */
    private boolean hasVoted;

    /** Determines whether or not the voter has checked in. */
    private boolean hasCheckedIn;

    /*****************************************************************
    Method that sets the voter's identification number.
     @param voterID The new voter ID of the voter.
    *****************************************************************/
    public void setID(int voterID) {
        this.voterID = voterID;
    }

    /*****************************************************************
    Method that adds on the time to the total time spend of the voter.
    @param time The amount of time to append to the voter's total time.
    *****************************************************************/
    public void addTime(double time) {
        this.timeSpent += time;
    }

    /*****************************************************************
    Method that sets the voter's time it took to vote. We require a
    use of non-primitive type so that we can can use Double's built-in
    classes.
    @param boothTime The amount of time it will take for the voter to
    vote.
    *****************************************************************/
    public void setBoothTime(Double boothTime) {
        this.boothTime = boothTime;
    }

    /*****************************************************************
    Method that sets the voter's time it took to check in. We require a
    use of non-primitive type so that we can can use Double's built-in
    classes.
    @param checkInTime The amount of time it will take for the voter to
    check in.
    *****************************************************************/
    public void setCheckInTime(Double checkInTime) {
        this.checkInTime = checkInTime;
    }

    /*****************************************************************
    Method that sets the voter's time it took to vote. We require a
    use of non-primitive type so that we can can use Double's built-in
    classes.
    @param status The VoterStatus that the voter is currently in.
    *****************************************************************/
    public void setStatus(VoterStatus status) {
        this.status = status;
    }

    /*****************************************************************
    Method that sets the voter's tolerance before they get mad and
    leave the sim.
    *****************************************************************/
    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    /*****************************************************************
    Method that sets whether or not the voter is mad and leaves the sim.
    @param isPissed The boolean value of whether or not the voter is mad.
    *****************************************************************/
    public void setPissed(boolean isPissed) {
        this.isPissed = isPissed;
    }

    /*****************************************************************
    Method that sets whether or not the voter has successfully voted.
    @param hasVoted The boolean value of whether the voter did vote.
    *****************************************************************/
    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    /*****************************************************************
    Method that sets whether or not the voter has checked in.
    @param hasCheckedIn The boolean value of if the voter has checked in.
    *****************************************************************/
    public void setCheckedIn(boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    /*****************************************************************
    Getter that returns whether or on the voter has exceeded their
    tolerance time.
    @return true if the voter is mad, false if they are still tolerant.
    *****************************************************************/
    public boolean isPissed() {
        return isPissed;
    }

    /*****************************************************************
    Getter that returns whether or on the voter has voted.
    @return true if the voter has voted, false if they are still in line.
    *****************************************************************/
    public boolean hasVoted() {
        return hasVoted;
    }

    /*****************************************************************
    Getter that returns whether or not the voter has checked in.
    @return true if the voter has checked in, false if they are still
    checking in.
    *****************************************************************/
    public boolean hasCheckedIn() {
        return hasCheckedIn;
    }

    /*****************************************************************
    Getter that returns the voter's tolerance time before they leave.
    @return the tolerance time of the voter as an int.
    *****************************************************************/
    public int getTolerance() {
        return tolerance;
    }

    /*****************************************************************
    Getter that returns the voter's time in the booth.
    @return the booth time as a Double object.
    *****************************************************************/
    public Double getBoothTime() {
        return boothTime;
    }

    /*****************************************************************
    Getter that returns the voter's time to check in.
    @return the check in time as a Double object.
    *****************************************************************/
    public Double getCheckInTime() {
        return checkInTime;
    }

    /*****************************************************************
    Getter that returns the voter's identification number.
    @return the booth time as an int.
    *****************************************************************/
    public int getVoterID() {
        return voterID;
    }

    /*****************************************************************
    Getter that returns the voter's total time spent in the simulation.
    @return the booth time as a double.
    *****************************************************************/
    public double getTimeSpent() {
        return timeSpent;
    }

    /*****************************************************************
    Getter that returns the voter's current VoterStatus.
    @return the current voter status as an enum from VoterStatus.
    *****************************************************************/
    public VoterStatus getStatus() {
        return status;
    }

}
