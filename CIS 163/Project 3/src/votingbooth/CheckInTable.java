package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/*****************************************************************
CheckInTable class. Responsible for handling voters that need to
check in, and then put them into the queue for the booth.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class CheckInTable implements ClockListener {

    /** ArrayList that contains all of the voters in the queue. */
    private ArrayList<Voter> Q;

    /** Contains the BoothLine instance. */
    private BoothLine boothQ;

    /** Contains the current voter that is being checked in. */
    private Voter current;

    /** Contains the record for the most amount of people in line. */
    private int maxQlength;

    /** Contains the avg check in time for each voter as an Integer. We
    don't use the primitive type so that we can use it's built in
    methods. */
    private Integer avgCheckInTime;

    /** Contains the check in time of the current voter. We
    don't use the primitive type so that we can use it's built in
    methods. */
    private Integer checkInTime;

    /** Contains the current amount of voters that have checked in. */
    private int checkedIn;

    /** Controls whether or not the check in table is in use. */
    private boolean inUse;

    /** Contains the random instance to obtain the desired mean. */
    private Random r;

    /*****************************************************************
    Default constructor that initializes all of the declared variables.
    @param avgCheckInTime The avg time it will take for a voter to
    check in.
    @param boothQ The current BoothLine object in the simulation.
    *****************************************************************/
    public CheckInTable(Integer avgCheckInTime, BoothLine boothQ) {

        /* Initializing all of our declared variables. */
        this.avgCheckInTime = avgCheckInTime;
        this.checkInTime = 0;
        this.checkedIn = 0;
        this.boothQ = boothQ;
        this.Q = new ArrayList<>();
        this.inUse = false;
        this.current = null;
        this.r = new Random();
    }

    /*****************************************************************
    Method that adds a voter to the check in table queue.
    @param person Voter to add to the check in table queue.
    *****************************************************************/
    public void addVoter(Voter person) {

        /* Setting the desired mean for the avg check in time. */
        person.setCheckInTime(avgCheckInTime.doubleValue()*0.1*r.nextGaussian() + avgCheckInTime);
        Q.add(person);
        if (Q.size() > maxQlength) {
            maxQlength = Q.size();
        }
    }

    /*****************************************************************
    Overriden method that handles when a tick of time goes by.
    @param tick The current tick the sim is on.
    *****************************************************************/
    @Override
    public void event(int tick) {

        /* We only want to handle when the queue is greater than 0. */
        if (Q.size() >= 1) {

            /* If the table is not being used, assign a voter to the table. */
            if (!inUse) {
                current = Q.remove(0);
                checkInTime = current.getCheckInTime().intValue();
                inUse = true;
            }

            /* For all those waiting in line, add 1 tick of time. */
            for (int i = 0; i < Q.size(); i++) {
                Q.get(i).addTime(1);
                if (Q.get(i).getTimeSpent() >= Q.get(i).getTolerance()) {
                    Q.get(i).setPissed(true);
                    Q.remove(i);
                }
            }

            /* Add time to the current voter as well. */
            current.addTime(1);

            /* Subtract 1 from the current voter's check in time. */
            checkInTime--;

            /* If the voter is done checking in, send them off to the
            booth line queue. */
            if (checkInTime <= 0) {
                current.setStatus(VoterStatus.WAITING_FOR_BOOTH);
                current.setCheckedIn(true);
                boothQ.addVoter(current);
                checkedIn++;
                inUse = false;
            }

        }
    }

    /*****************************************************************
    Method that sets the new avg check in time for all voters.
    @param time The new avg check in time for voters as in int.
    *****************************************************************/
    public void setAvgCheckInTime(int time) {
        this.avgCheckInTime = time;
    }

    /*****************************************************************
    Getter that returns the record for the maximum amount of voters
    in the queue.
    @return The record for the maximum amount of voters in the queue
    as an int.
    *****************************************************************/
    public int getMaxQlength() {
        return maxQlength;
    }

    /*****************************************************************
    Getter that returns the current size of the voter queue.
    @return The current size of the voter queue as an int.
    *****************************************************************/
    public int getVoterQ() {
        return Q.size();
    }

    /*****************************************************************
    Getter that returns the current number of voters that checked in.
    @return The current amount of checked in voters as an int.
    *****************************************************************/
    public int getCheckedIn() {
        return checkedIn;
    }

}
