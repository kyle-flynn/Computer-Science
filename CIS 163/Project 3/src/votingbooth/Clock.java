package votingbooth;

/*****************************************************************
Clock class. Responsible for keeping time in the simulation.
@author Roger Ferguson
@version 1.0
*****************************************************************/
public class Clock {

    /** Contains all ClockListeners inside the sim as an array. */
    private ClockListener[] myListeners;

    /** Contains the current amount of listeners in the sim. */
    private int numListeners;

    /** Contains maximum amount of allowed listeners. Initialized
    here because it will never change. */
    private int MAX = 100;

    /** Contains the current tick of time that the sim is on. */
    private int currentTick;

    /*****************************************************************
    Default constructor that initializes all of the needed variables.
    *****************************************************************/
    public Clock() {
        numListeners = 0;
        myListeners = new ClockListener[MAX];
        currentTick = 0;
    }

    /*****************************************************************
    Method that runs the sim for a specific amount of time.
    @param ticksToGo The time to run the sim.
    *****************************************************************/
    public void run(int ticksToGo) {
        for (int time = 0; time <= ticksToGo; time++) {
            for (int j = 0; j < numListeners; j++) {
                myListeners[j].event(time + currentTick);
            }
        }

        /* Appends the ticks to go to the current tick, so that we may
        add more time to the clock, or finish the sim. */
        currentTick += ticksToGo + 1;
    }

    /*****************************************************************
    Method that starts the master clock and runs the rest of the sim.
    @param endingTime The time at which the sim ends.
    *****************************************************************/
    public void start(int endingTime) {
        for (int time = currentTick; time <= endingTime; time++) {
            for (int j = 0; j < numListeners; j++) {
                myListeners[j].event(time);
            }
        }
    }

    /*****************************************************************
    Method that adds a ClockListener to the master clock.
    @param cl The ClockListener to add.
    *****************************************************************/
    public void add(ClockListener cl) {
        myListeners[numListeners] = cl;
        numListeners++;
    }

    /*****************************************************************
    Method that resets the ticks of the master clock.
    *****************************************************************/
    public void reset() {
        currentTick = 0;
    }

    /*****************************************************************
    Method that clears the amount of ClockListeners and resets the
    array.
    *****************************************************************/
    public void clearListeners() {
        myListeners = new ClockListener[MAX];
        numListeners = 0;
    }

    /*****************************************************************
    Method that sets the number of listeners in the sim.
    @param numListeners The number of listeners to set the sim into.
    *****************************************************************/
    public void setNumListeners(int numListeners) {
        this.numListeners = numListeners;
    }

    /*****************************************************************
    Method that sets the CLockListener array to a new array.
    @param myListeners The new ClockListener array.
    *****************************************************************/
    public void setMyListeners(ClockListener[] myListeners) {
        this.myListeners = myListeners;
    }

    /*****************************************************************
    Getter that returns the entire ClockListener array.
    @return The ClockListener array for the sim.
    *****************************************************************/
    public ClockListener[] getMyListeners() {
        return myListeners;
    }

    /*****************************************************************
    Getter that returns the current number of listeners in the sim.
    @return The current number of ClockListeners in the sim as an int.
    *****************************************************************/
    public int getNumListeners() {
        return numListeners;
    }

    /*****************************************************************
    Getter that returns the maximum amount of allowed listeners.
    @return The number of allowed listeners as an int.
    *****************************************************************/
    public int getMAX() {
        return MAX;
    }

}