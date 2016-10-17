package votingbooth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author   Roger Ferguson
 */
public class ClockTimer {

    private TimerListener timerListener;

    private ClockListener[] myListeners;
    private int numListeners;
    private int MAX = 100;
    private int currentTime;
    private int endingTime;

    private Timer timer;

    public ClockTimer() {
        timerListener = new TimerListener();

        numListeners = 0;
        myListeners = new ClockListener[MAX];
        currentTime = 0;
        endingTime = 0;
        timer = new Timer(1, timerListener);
    }

    public void run(int endingTime) {
        this.endingTime = endingTime;
        timer.start();
    }

    public void add(ClockListener cl) {
        myListeners[numListeners] = cl;
        numListeners++;
    }

    public void stop() {
        timer.stop();
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

            if (currentTime >= endingTime) {
                timer.stop();
            }

        }

    }

}
