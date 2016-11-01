package votingbooth;

/**
 * @author   Roger Ferguson
 */
public class Clock {

    private ClockListener[] myListeners;
    private int numListeners;
    private int MAX = 100;
    private int currentTick;

    public Clock() {
        numListeners = 0;
        myListeners = new ClockListener[MAX];
        currentTick = 0;
    }

    public void run(int ticksToGo) {
        for (int currentTime = 0; currentTime <= ticksToGo; currentTime++) {
            for (int j = 0; j < numListeners; j++) {
                myListeners[j].event(currentTime + currentTick);
            }
        }
        currentTick += ticksToGo + 1;
    }

    public void start(int endingTime) {
        for (int currentTime = currentTick; currentTime <= endingTime; currentTime++) {
            for (int j = 0; j < numListeners; j++) {
                myListeners[j].event(currentTime);
            }
        }
    }

    public void add(ClockListener cl) {
        myListeners[numListeners] = cl;
        numListeners++;
    }

    public void reset() {
        currentTick = 0;
    }

    public void clearListeners() {
        myListeners = new ClockListener[MAX];
        numListeners = 0;
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