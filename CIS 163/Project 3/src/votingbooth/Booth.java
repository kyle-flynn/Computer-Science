package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author   Roger Ferguson
 */
public class Booth implements ClockListener {
    private Voter person;
    private int completed;
    private int averageBoothTime;
    private boolean inUse;
    private int timeToVote;

    private ArrayList<Double> totalTimes;

    Random r = new Random();

    public Booth(int averageBoothTime) {
        this.averageBoothTime = averageBoothTime;
        this.completed = 0;
        this.inUse = false;

        this.totalTimes= new ArrayList<>();
    }

    public void add(Voter voter) {
        person = voter;
        inUse = true;
        person.setBoothTime(averageBoothTime*0.1*r.nextGaussian() + averageBoothTime);
        person.setStatus(VoterStatus.VOTING);
        timeToVote = person.getBoothTime().intValue();
    }

    public void event(int tick) {
        if (inUse) {
            person.addTime(1);
            timeToVote--;
            if (timeToVote <= 0) {
                person.setStatus(VoterStatus.DONE);
                person.setVoted(true);
                totalTimes.add(person.getTimeSpent());
                completed++;
                inUse = false;
            }
        }
    }

    public void setAverageBoothTime(int time) {
        this.averageBoothTime = time;
    }

    public ArrayList<Double> getCompletedTimes() {
        return totalTimes;
    }

    public boolean inUse() {
        return inUse;
    }

    public int getThroughPut() {
        return completed;
    }
}
