package votingbooth;

import java.util.ArrayList;

/*****************************************************************
Booth Line class. Responsible for handling all voters in the queue
for a booth.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class BoothLine implements ClockListener {

    /** ArrayList that contains all voters inside of the queue. */
    private ArrayList<Voter> Q;

    /** Contains all of the Booth objects in the sim as an array. */
    private Booth[] booths;

    /** Contains the record for the most amount of people in line. */
    private int maxQlength;

    /*****************************************************************
    Default constructor that initializes all of the declared variables.
    @param booths The array of Booth objects in the sim.
    *****************************************************************/
    public BoothLine(Booth[] booths) {
        this.booths = booths;
        this.Q = new ArrayList<>();
    }

    /*****************************************************************
    Method that adds a voter to the booth line queue..
    @param person Voter to add to the booth line queue.
    *****************************************************************/
    public void addVoter(Voter person) {
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

            /* For each voter inside of the queue, add 1 tick of time. */
            for (int i = 0; i < Q.size(); i++) {
                Q.get(i).addTime(1);
                if (Q.get(i).getTimeSpent() >= Q.get(i).getTolerance()) {

                    Q.get(i).setPissed(true);
                    Q.remove(i);
                }
            }

            for (Booth b : booths) {

                /* If there is a booth not in use, assign a voter to that booth. */
                if (!b.inUse()) {
                    if (Q.get(0).getStatus() == VoterStatus.WAITING_FOR_BOOTH) {
                        Voter person = Q.remove(0);
                        b.add(person);

                        /* We don't want to run the rest of the loop. */
                        break;
                    }
                }
            }

        }
    }

    /*****************************************************************
    Method that sets the new Booth object array for this class.
    @param booths The new Booth object array
    *****************************************************************/
    public void setBooths(Booth[] booths) {

        /* Setting the new size of the array. */
        this.booths = new Booth[booths.length];

        /* Setting the new contents of the array. */
        for (int i = 0; i < booths.length; i++) {
            this.booths[i] = booths[i];
        }
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
    public int getLeft() {
        return Q.size();
    }

}
