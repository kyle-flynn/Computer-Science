package votingbooth;

import java.util.ArrayList;
import java.util.List;

/*****************************************************************
Voter Producer class. Responsible for producing voters and initial
placement of voters to a check in table.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class VoterProducer implements ClockListener {

    /** Controls when the next voter should be produced */
    private int nextPerson;

    /** Contains the amount of booths in the simulation as a 2d array */
    private Booth[] booths;

    /** Contains the time on average to produce a voter */
    private int numOfTicksNextPerson;

    /** Contains the average time a voter can tolerate in line */
    private int toleranceTime;

    /** ArrayList that holds available check-in tables */
    private ArrayList<CheckInTable> tables;

    /** ArrayList that holds all regular voters */
    private ArrayList<Voter> normalVoters;

    /** ArrayList that holds all limited time voters */
    private ArrayList<Voter> limitedVoters;

    /** ArrayList that holds all special needs voters */
    private ArrayList<Voter> specialVoters;

    /** ArrayList that holds all super special needs voters */
    private ArrayList<Voter> superSpecialVoters;

    /** ArrayList that holds all voters, despite type */
    private ArrayList<Voter> voters;

    /** Integer that holds the maximum amount of normal voters. We don't
    use the primitive type so we can use it's built-in methods. */
    private Integer maxNormalVoters;

    /** Integer that holds the maximum amount of limited voters. We don't
    use the primitive type so we can use it's built-in methods. */
    private Integer maxLimitedVoters;

    /** Integer that holds the maximum amount of special voters. We don't
    use the primitive type so we can use it's built-in methods. */
    private Integer maxSpecialVoters;

    /** Integer that holds the maximum amount of super special voters. We don't
    use the primitive type so we can use it's built-in methods. */
    private Integer maxSuperSpecialVoters;

    /** Contains the amount of generated voters, so we may assign an ID */
    private int count;

    /*****************************************************************
    Default constructor that initializes all of the needed variables.
    @param booths The 2d array of Booth objects in the simulation.
    @param numOfTicksNextPerson The amount of ticks between voters.
    @param toleranceTime The avg time before a voter leaves the sim.
    *****************************************************************/
    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int toleranceTime,
                         Integer totalPeople) {

        /* Initializing all of our declared variables. */
        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.toleranceTime = toleranceTime;
        this.tables = new ArrayList<>();
        this.normalVoters = new ArrayList<>();
        this.limitedVoters = new ArrayList<>();
        this.specialVoters = new ArrayList<>();
        this.superSpecialVoters = new ArrayList<>();
        this.voters = new ArrayList<>();

        /* We set our maximum number of allowed voter type by multiplying
        the total amount of people in the simulation by the desired
        percent. This is where the use of non-primitive types comes in
        use. */
        this.maxNormalVoters = ((Double)(totalPeople.doubleValue()
                * 0.7)).intValue();
        this.maxLimitedVoters = ((Double)(totalPeople.doubleValue()
                * 0.2)).intValue();
        this.maxSpecialVoters = ((Double)(totalPeople.doubleValue()
                * 0.05)).intValue();
        this.maxSuperSpecialVoters = ((Double)(totalPeople.doubleValue()
                * 0.05)).intValue();
        this.count = 0;
        this.nextPerson = 0;
    }

    /*****************************************************************
    Method that handles an event when a 'tick" of time goes by.
    @param tick Amount of time in simulation.
    *****************************************************************/
    public void event(int tick) {

        /* This dictates when to generate a new voter */
        if (nextPerson <= tick) {

            /* After a voter is generated, generate the next at the time
            of this event plus the number of required ticks between voters. */
            nextPerson = tick + numOfTicksNextPerson;

            /** The current voter that has just been generated. Only
            locally declared. */
            Voter person;

            /* Determines what type of voter to generate based on maximum
            number of allowed voters for that type of voter.
            */
            if (limitedVoters.size() < maxLimitedVoters) {
                person = new LimitedTimeVoter();
                limitedVoters.add(person);
            } else if (specialVoters.size() < maxSpecialVoters) {
                person = new SpecialNeedsVoter();
                specialVoters.add(person);
            } else if (superSpecialVoters.size() < maxSuperSpecialVoters) {
                person = new SuperSpecialNeedsVoter();
                superSpecialVoters.add(person);
            } else {
                person = new Voter();
                normalVoters.add(person);
            }

            /* Add the newly generated voter to the 'all' voter ArrayList. */
            voters.add(person);
            count++;

            /* Here is where we assign the voter to a table. The algorithm
            assigns based on which table has the least amount of voters. */
            for (int i = 0; i < tables.size(); i++) {
                if (i < tables.size() - 1) {

                    /* Here we compare the current and next table voter queue. */
                    if (tables.get(i).getVoterQ() <= tables.get(i+1).getVoterQ()) {
                        tables.get(i).addVoter(person);

                        /* Break the loop because once the voter is added, do no more! */
                        break;
                    }
                } else {

                    /* Here we compare the current and first table voter queue. */
                    if (tables.get(i).getVoterQ() <= tables.get(0).getVoterQ()) {
                        tables.get(i).addVoter(person);

                        /* Break the loop because once the voter is added, do no more! */
                        break;
                    }
                }
            }

            /* Setting properties of the voter for later use. */
            person.setID(count);
            person.setTolerance(toleranceTime);
            person.setCheckedIn(false);
            person.setVoted(false);
            person.setPissed(false);
            person.setStatus(VoterStatus.CHECKING_IN);
        }
    }

    /*****************************************************************
    Method that sets the tolerance time for newly created voters.
    @param time The amount of time to set the tolerance to.
    *****************************************************************/
    public void setToleranceTime(int time) {
        this.toleranceTime = time;
    }

    /*****************************************************************
     Method that sets the generation time between voters.
     @param ticks The amount of time between generated voters.
     *****************************************************************/
    public void setNumOfTicksNextPerson(int ticks) {
        this.numOfTicksNextPerson = ticks;
    }

    /*****************************************************************
    Method that sets the 'this' booth array to an outside 2d Booth
    object array.
    @param booths Other Booth 2d array to set equal to.
    *****************************************************************/
    public void setBooths(Booth[] booths) {

        /* Set the new size of the array */
        this.booths = new Booth[booths.length];

        /* Set contents of newly created array */
        for (int i = 0; i < booths.length; i++) {
            this.booths[i] = booths[i];
        }
    }

    /*****************************************************************
    Method that sets the 'this' CheckInTable List to an outside
    CheckInTable List.
    @param newTables Other CheckInTable List to set equal to.
    *****************************************************************/
    public void setTables(List<CheckInTable> newTables) {

        /* Reset our List */
        tables.clear();

        /* Set new contents of our ArrayList */
        for (CheckInTable table : newTables) {
            tables.add(table);
        }
    }

    /*****************************************************************
    Method that adds a CheckInTable to the 'this' CheckInTable List.
    @param table CheckInTable to add to the List.
    *****************************************************************/
    public void addTable(CheckInTable table) {
        tables.add(table);
    }

    /*****************************************************************
    Getter that returns number of voters that have completed the sim.
    @return Number of completed voters as an int.
    *****************************************************************/
    public int getAllThrougPut() {
        int completed = 0;
        for (Booth b : booths) {
            completed += b.getThroughPut();
        }
        return completed;
    }

    /*****************************************************************
    Getter that returns number of voters that have completed the sim.
    @return Number of completed voters as an int.
    *****************************************************************/
    public ArrayList<Voter> getNormalVoters() {
        return normalVoters;
    }


    /*****************************************************************
    Getter that returns the Limited Voters ArrayList.
    @return the Limited Voters ArrayList.
    *****************************************************************/
    public ArrayList<Voter> getLimitedVoters() {
        return limitedVoters;
    }

    /*****************************************************************
    Getter that returns the Special Needs Voters ArrayList.
    @return the Special Needs Voters ArrayList.
    *****************************************************************/
    public ArrayList<Voter> getSpecialVoters() {
        return specialVoters;
    }

    /*****************************************************************
    Getter that returns the Super Special Needs Voters ArrayList.
    @return the Super Special Needs Voters ArrayList.
    *****************************************************************/
    public ArrayList<Voter> getSuperSpecialVoters() {
        return superSpecialVoters;
    }

    /*****************************************************************
    Getter that returns the ArrayList that contains all the voters.
    @return
    *****************************************************************/
    public ArrayList<Voter> getVoters() {
        return voters;
    }

}
