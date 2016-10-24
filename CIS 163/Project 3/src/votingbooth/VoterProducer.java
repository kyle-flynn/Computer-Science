package votingbooth;

import java.util.ArrayList;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth[] booths;
    private int numOfTicksNextPerson;
    private int toleranceTime;

    private ArrayList<CheckInTable> tables;

    private int count;

    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int toleranceTime) {

        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.toleranceTime = toleranceTime;
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
            person.setTolerance(toleranceTime);

            // TODO - Add leave time for the voter

            // Here send the voter to a checkIn A-L or M-Z
            for (int i = 0; i < tables.size(); i++) {
                if (i < tables.size() - 1) {
                    if (tables.get(i).getVoterQ() <= tables.get(i+1).getVoterQ()) {
                        tables.get(i).addVoter(person);
                        break;
                    }
                } else {
                    if (tables.get(i).getVoterQ() <= tables.get(0).getVoterQ()) {
                        tables.get(i).addVoter(person);
                        break;
                    }
                }
            }
            person.setStatus(VoterStatus.CHECKING_IN);
        }
    }

    public void addTable(CheckInTable table) {
        tables.add(table);
    }

    public int getAllThrougPut() {
        int completed = 0;
        for (Booth b : booths) {
            completed += b.getThroughPut();
        }
        return completed;
    }

}
