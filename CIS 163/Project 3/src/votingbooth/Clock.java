package votingbooth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author   Roger Ferguson
 */
public class Clock {

    private TimerListener timerListener;

    private ClockListener[] myListeners;
    private int numListeners;
    private int MAX = 100;
    private int currentTime;

    private Timer timer;

    public Clock() {
        timerListener = new TimerListener();

        numListeners = 0;
        myListeners = new ClockListener[MAX];
        currentTime = 0;
        timer = new Timer(1000, timerListener);
    }

    public void run(int endingTime) {
        timer.start();
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

    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            currentTime++;
            for (int j = 0; j < numListeners; j++) {
                myListeners[j].event(currentTime);
            }
        }

    }

}
