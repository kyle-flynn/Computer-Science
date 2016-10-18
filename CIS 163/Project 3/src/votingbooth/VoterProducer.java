package votingbooth;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth booth;
    private int numOfTicksNextPerson;
    private int averageBoothTime;

    private ArrayList<Double> avgBoothTimes;

    private Random r = new Random();

    public VoterProducer(Booth booth,
                         int numOfTicksNextPerson,
                         int averageBoothTime) {

        this.booth = booth;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        this.avgBoothTimes = new ArrayList<>();
        //r.setSeed(13);    // This will cause the same random numbers
    }

    public void event(int tick) {
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            Voter person = new Voter();

            person.setBoothTime(averageBoothTime*0.5*r.nextGaussian() + averageBoothTime +.5);
            person.setTickTime(tick);
            booth.add(person);
            avgBoothTimes.add(person.getBoothTime());
            //	person.setDestination(theLocationAfterTheBooth);  // You can save off where the voter should go.
        }
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
