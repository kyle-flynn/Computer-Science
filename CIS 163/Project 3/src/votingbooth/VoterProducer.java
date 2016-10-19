package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth[] booths;
    private int numOfTicksNextPerson;
    private int averageBoothTime;

    private ArrayList<Double> avgBoothTimes;
    private ArrayList<CheckInTable> tables;

    private int count;

    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int averageBoothTime) {

        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        this.avgBoothTimes = new ArrayList<>();
        this.tables = new ArrayList<>();

        this.count = 0;
        //r.setSeed(13);    // This will cause the same random numbers
    }

    public void event(int tick) {
        /** This dictates when to generate a new voter */
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            Voter person = new Voter();
            count++;

            person.setID(count);

            // Here send the voter to a checkIn A-L or M-Z
            for (int i = 0; i < tables.size(); i++) {
                if (i < tables.size() - 1) {
                    if (tables.get(i).getVoterQ() <= tables.get(i+1).getVoterQ()) {
                        System.out.println("Voter " + person.getVoterID() + " Checking in on table " + i);
                        tables.get(i).addVoter(person);
                        break;
                    }
                } else {
                    if (tables.get(i).getVoterQ() <= tables.get(0).getVoterQ()) {
                        tables.get(i).addVoter(person);
                        System.out.println("Voter " + person.getVoterID() + " Checking in on table " + i);
                        break;
                    }
                }
            }

            // Here set the voter booth time and stuff
            person.setTickTime(tick);
            person.setStatus(VoterStatus.CHECKING_IN);

            avgBoothTimes.add(person.getBoothTime());
        }
    }

    public void addTable(CheckInTable table) {
        tables.add(table);
    }

    public double getAverageBoothTime() {
        if (avgBoothTimes.size() > 0) {
            double sum = 0.0;
            for (Double times : avgBoothTimes) {
                sum += times;
            }
            return sum / avgBoothTimes.size();
        }
        return 0.0;
    }

    public int getAllThrougPut() {
        int completed = 0;
        for (Booth b : booths) {
            completed += b.getThroughPut();
        }
        return completed;
    }

    public int getAllLeft() {
        int left = 0;
        for (Booth b : booths) {
            left += b.getLeft();
        }
        return left;
    }

    public int getBoothQ() {
        int boothQ = 0;
        for (Booth b : booths) {
            boothQ += b.getMaxQlength();
        }
        return boothQ;
    }

}
