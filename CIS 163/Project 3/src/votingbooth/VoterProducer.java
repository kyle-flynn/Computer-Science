package votingbooth;

import votingbooth.gui.BasicSimulatorController;

import java.util.ArrayList;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth[] booths;
    private int numOfTicksNextPerson;

    private int averageBoothTime;
    private int voterSplit = 0;

    private int toleranceTime;


    private int toleranceTime;

<<<<<<< HEAD


    private ArrayList<Double> avgBoothTimes;
=======
>>>>>>> origin/master
    private ArrayList<CheckInTable> tables;

    private BasicSimulatorController basicController;

    private int count;

    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int toleranceTime) {

        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.toleranceTime = toleranceTime;
        this.tables = new ArrayList<>();
        b = new BasicSimulatorController();

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

    /**
     * Differentiate between voter types with 10% for Special Needs
     * 20% for Limited Time, and 70% for the rest
     */
    public void calculateVoter(){
        //Get the total number
        voterSplit = (basicController.totalTimeSec / basicController.nextPerson);

    }


}
