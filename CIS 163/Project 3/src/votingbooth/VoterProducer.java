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

    private Random r = new Random();

    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int averageBoothTime) {

        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        this.avgBoothTimes = new ArrayList<>();
        this.tables = new ArrayList<>();
        //r.setSeed(13);    // This will cause the same random numbers
    }

    public void event(int tick) {
        /** This dictates when to generate a new voter */
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            Voter person = new Voter();

            // Here send the voter to a checkIn A-L or M-Z
            for (int i = 0; i < tables.size(); i++) {
                if (i < tables.size() - 1) {
                    if (tables.get(i).getVoterQ() <= tables.get(i+1).getVoterQ()) {
                        tables.get(i).addVoter(person);
                    } else {
                        tables.get(i+1).addVoter(person);
                    }
                }
            }

            // Here set the voter booth time and stuff
            person.setBoothTime(averageBoothTime*0.5*r.nextGaussian() + averageBoothTime +.5);
            person.setTickTime(tick);

            // TODO - Dictate which person goes to which booth
            booths[0].add(person);
            avgBoothTimes.add(person.getBoothTime());
            //	person.setDestination(theLocationAfterTheBooth);  // You can save off where the voter should go.
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

}
