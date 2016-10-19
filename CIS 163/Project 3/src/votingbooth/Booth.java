package votingbooth;

import java.util.ArrayList;
/**
 * @author   Roger Ferguson
 */
public class Booth implements ClockListener {
    private ArrayList<Voter> Q = new ArrayList<Voter>();

    private int timeOfNextEvent = 0;
    private int maxQlength = 0;
    private Voter person;   // this is the person at the booth.
    private int completed = 0;
    private int averageBoothTime;

    public Booth(int averageBoothTime) {
        this.averageBoothTime = averageBoothTime;
    }

    public void add (Voter person) {
        Q.add(person);
        if (Q.size() > maxQlength)
            maxQlength = Q.size();
    }

    public void event (int tick) {
        if (tick >= timeOfNextEvent) {
            if (Q.size() >= 1) {
                if (Q.get(0).getStatus() == VoterStatus.WAITING_FOR_BOOTH) {
                    person = Q.remove(0);
                    timeOfNextEvent = tick + (int) (person.getBoothTime() + 1);
                    completed++;
                    person.addTime(person.getBoothTime());
                    person.setStatus(VoterStatus.DONE);
                    System.out.println("Voter " + person.getVoterID() + " done. Total time " + person.getTimeSpent());
                }
            }
        }
    }

    public int getAverageBoothTime() {
        return averageBoothTime;
    }

    public int getLeft() {
        return Q.size();
    }

    public int getMaxQlength() {
        return maxQlength;
    }

    public int getThroughPut() {
        return completed;
    }
}
