package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Clock {

    private ClockListener[] myListeners;
    private int numListeners;
    private int MAX = 100;

    public Clock() {
        numListeners = 0;
        myListeners = new ClockListener[MAX];
    }

    public void run(int endingTime) {
        for (int currentTime = 0; currentTime <= endingTime; currentTime++) {
            for (int j = 0; j < numListeners; j++)
                myListeners[j].event(currentTime);
        }
    }

    public void add(ClockListener cl) {
        myListeners[numListeners] = cl;
        numListeners++;
    }

    public ClockListener[] getMyListeners() {
        return myListeners;
    }

    public void setMyListeners(ClockListener[] myListeners) {
        this.myListeners = myListeners;
    }

    public int getNumListeners() {
        return numListeners;
    }

    public void setNumListeners(int numListeners) {
        this.numListeners = numListeners;
    }

    public int getMAX() {
        return MAX;
    }

}
