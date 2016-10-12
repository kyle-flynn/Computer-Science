package votingbooth;

import java.util.Random;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth booth;
    private int numOfTicksNextPerson;
    private int averageBoothTime;

    private Random r = new Random();

    public VoterProducer(Booth booth,
                         int numOfTicksNextPerson,
                         int averageBoothTime) {

        this.booth = booth;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        //r.setSeed(13);    // This will cause the same random numbers
    }

    public void event(int tick) {
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            Voter person = new Voter();

            int rNumber = (int)(Math.random() * 100);

            person.setBoothTime(averageBoothTime*0.5*r.nextGaussian() + averageBoothTime +.5);
            person.setTickTime(tick);
            booth.add(person);

            //	person.setDestination(theLocationAfterTheBooth);  // You can save off where the voter should go.
        }
    }

}
