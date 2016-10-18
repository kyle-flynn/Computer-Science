package votingbooth;

import java.util.ArrayList;

/**
 * Created by kylef_000 on 10/17/2016.
 */
public class CheckInTable implements ClockListener {

    private ArrayList<Voter> Q;
    private int maxQlength;
    private int checkInTime;
    private int checkedIn;
    private int nextEvent;

    public CheckInTable(int checkInTime) {
        this.checkInTime = checkInTime;
        this.checkedIn = 0;
        this.nextEvent = 0;
        this.Q = new ArrayList<>();
    }

    public void addVoter(Voter person) {
        Q.add(person);
        if (Q.size() > maxQlength) {
            maxQlength = Q.size();
        }

    }

    @Override
    public void event(int tick) {
        if (tick >= nextEvent) {
            if (Q.size() >= 1) {
                Q.remove(0);
                nextEvent = checkInTime + tick;
                checkedIn++;
            }
        }
    }

    public int getVoterQ() {
        return Q.size();
    }

    public int getCheckedIn() {
        return checkedIn;
    }

}
