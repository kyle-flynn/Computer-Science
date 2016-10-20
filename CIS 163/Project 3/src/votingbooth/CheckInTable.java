package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kylef_000 on 10/17/2016.
 */
public class CheckInTable implements ClockListener {

    private ArrayList<Voter> Q;
    private BoothLine boothQ;
    private int maxQlength;
    private int checkInTime;
    private int checkedIn;
    private int nextEvent;

    private Random r = new Random();

    private int count;

    public CheckInTable(int checkInTime, BoothLine boothQ) {
        this.checkInTime = checkInTime;
        this.checkedIn = 0;
        this.nextEvent = 0;
        this.boothQ = boothQ;
        this.Q = new ArrayList<>();

        this.count = 0;
    }

    public void addVoter(Voter person) {
        Q.add(person);
        if (Q.size() > maxQlength) {
            maxQlength = Q.size();
        }
    }

    public int getMaxQlength() {
        return maxQlength;
    }

    @Override
    public void event(int tick) {
        if (tick >= nextEvent) {
            if (Q.size() >= 1) {
                Voter person = Q.remove(0);

                person.addTime(checkInTime);
                person.setStatus(VoterStatus.WAITING_FOR_BOOTH);

                boothQ.addVoter(person);
                nextEvent = checkInTime + tick;
                checkedIn++;
            }
        }
    }

    public int getVoterQ() {
        return Q.size();
    }

}
