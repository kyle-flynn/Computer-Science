package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kylef_000 on 10/17/2016.
 */
public class CheckInTable implements ClockListener {

    private ArrayList<Voter> Q;
    private Booth[] booths;
    private int maxQlength;
    private int checkInTime;
    private int checkedIn;
    private int nextEvent;

    private Random r = new Random();

    private int count;

    public CheckInTable(int checkInTime, Booth[] booths) {
        this.checkInTime = checkInTime;
        this.checkedIn = 0;
        this.nextEvent = 0;
        this.booths = booths;
        this.Q = new ArrayList<>();

        this.count = 0;
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
                Voter person = Q.remove(0);

                person.addTime(checkInTime);
                person.setTickTime(tick);
                person.setStatus(VoterStatus.WAITING_FOR_BOOTH);

                // Here we dictate which booth the voter will go to
                for (int i = 0; i < booths.length; i++) {
                    if (i < booths.length - 1) {
                        // If the current booth is less than the next booth, assign voter.
                        if (booths[i].getMaxQlength() <= booths[i+1].getMaxQlength()) {
                            booths[i].add(person);
                            person.setBoothTime(booths[i].getAverageBoothTime()*0.5*r.nextGaussian() + booths[i].getAverageBoothTime() +.5);
                            break;
                        }
                    } else {
                        if (booths[i].getMaxQlength() <= booths[0].getMaxQlength()) {
                            booths[i].add(person);
                            person.setBoothTime(booths[i].getAverageBoothTime()*0.5*r.nextGaussian() + booths[i].getAverageBoothTime() +.5);
                            break;
                        }
                    }
                }

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

    private Booth[] getBooths() {
        return booths;
    }

}
