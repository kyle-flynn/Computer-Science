package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/*****************************************************************
Booth class. Responsible for handling when a voter comes out of a
booth line.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class Booth implements ClockListener {

    /** Contains the current voter in the booth. */
    private Voter person;

    /** Contains the amount of voters that the booth has completed. */
    private int completed;

    /** Contains the avg booth time for each voter. */
    private int averageBoothTime;

    /** Controls whether or not the booth is in use. */
    private boolean inUse;

    /** Contains current voter's avg booth time. */
    private int timeToVote;

    /** Double ArrayList that contains all completion times. */
    private ArrayList<Double> totalTimes;

    /** Contains the random instance to obtain the desired mean. */
    Random r = new Random();

    /*****************************************************************
    Default constructor that initializes all of the declared variables.
    @param averageBoothTime The avg time it takes to vote for each
    voter.
    *****************************************************************/
    public Booth(int averageBoothTime) {
        this.averageBoothTime = averageBoothTime;
        this.completed = 0;
        this.inUse = false;
        this.totalTimes= new ArrayList<>();
    }

    /*****************************************************************
    Method that adds a voter to the booth.
    @param voter Voter to add to the booth.
    *****************************************************************/
    public void add(Voter voter) {

        /* Setting the new current voter. */
        person = voter;
        inUse = true;
        person.setBoothTime(
                averageBoothTime*0.1*r.nextGaussian()
                        + averageBoothTime);
        person.setStatus(VoterStatus.VOTING);
        timeToVote = person.getBoothTime().intValue();
    }

    /*****************************************************************
    Overriden method that handles when a tick of time goes by.
    @param tick The current tick the sim is on.
    *****************************************************************/
    public void event(int tick) {

        /* We only want to handle when the booth is in use. */
        if (inUse) {
            person.addTime(1);
            timeToVote--;

            /* Once finished voting, send the person off! */
            if (timeToVote <= 0) {
                person.setStatus(VoterStatus.DONE);
                person.setVoted(true);
                totalTimes.add(person.getTimeSpent());
                completed++;
                inUse = false;
            }
        }
    }

    /*****************************************************************
    Method that sets the new avg booth time for all voters.
    @param time The new avg booth time for voters as in int.
    *****************************************************************/
    public void setAverageBoothTime(int time) {
        this.averageBoothTime = time;
    }

    /*****************************************************************
    Getter that returns all completion times for each voter that voted.
    @return The Double ArrayList that contains each voter's times.
    *****************************************************************/
    public ArrayList<Double> getCompletedTimes() {
        return totalTimes;
    }

    /*****************************************************************
    Getter that returns whether or not the booth is in use by a voter.
    @return true if the booth is in use, false if it is not in use.
    *****************************************************************/
    public boolean inUse() {
        return inUse;
    }

    /*****************************************************************
    Getter that returns the amount of voters the booth has completed.
    @return The number of voters the booth completed as an int.
    *****************************************************************/
    public int getThroughPut() {
        return completed;
    }
}
