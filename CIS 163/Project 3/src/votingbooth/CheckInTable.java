package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kylef_000 on 10/17/2016.
 */
public class CheckInTable implements ClockListener {

    private ArrayList<Voter> pissed;
    private ArrayList<Voter> Q;
    private BoothLine boothQ;
    private Voter current;
    private int maxQlength;
    private Integer avgCheckInTime;
    private Integer checkInTime;
    private int checkedIn;
    private int nextEvent;
    private boolean inUse;

    private Random r = new Random();

    public CheckInTable(Integer avgCheckInTime, BoothLine boothQ) {
        this.avgCheckInTime = avgCheckInTime;
        this.checkInTime = 0;
        this.checkedIn = 0;
        this.nextEvent = 0;
        this.boothQ = boothQ;
        this.Q = new ArrayList<>();
        this.inUse = false;
        this.current = null;
        this.pissed = new ArrayList<>();
    }

    public void addVoter(Voter person) {
        person.setCheckInTime(avgCheckInTime.doubleValue()*0.1*r.nextGaussian() + avgCheckInTime);
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
        if (Q.size() >= 1) {
            if (!inUse) {
                current = Q.remove(0);
                checkInTime = current.getCheckInTime().intValue();
                inUse = true;
            }


            for (int i = 0; i < Q.size(); i++) {
                Q.get(i).addTime(1);
                if (Q.get(i).getTimeSpent() >= Q.get(i).getTolerance()) {
                    // fuck this shit im out
                    System.out.println("Voter " + Q.get(i).getVoterID() + ": FUCK THIS");
                    peoplePissed(Q.remove(i));
                }
            }

            current.addTime(1);
            checkInTime--;
            if (checkInTime <= 0) {
                current.setStatus(VoterStatus.WAITING_FOR_BOOTH);
                boothQ.addVoter(current);
                checkedIn++;
                inUse = false;
            }

        }
    }

    /**
     * Calculate the people that are 'pissed' and leave the Voting Q. Parses through the
     * ArrayList and ticks the time until it gets to the "tolerance" point. If the tolerance
     * is exceeded
     *
     *
     * @param person is of type Voter to be used with the ArrayList
     * @return the ArrayList
     */
    public ArrayList<Voter> peoplePissed(Voter person){
        if (pissed.size() >= 1) {
            for (int i = 0; i < pissed.size(); i++) {
                pissed.get(i).addTime(1);
                if (pissed.get(i).getTimeSpent() >= pissed.get(i).getTolerance()) {
                    pissed.add(person);

                }
            }
        }
        return pissed;
    }

    public int getVoterQ() {
        return Q.size();
    }

}
