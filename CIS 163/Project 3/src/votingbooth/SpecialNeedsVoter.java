package votingbooth;

/*****************************************************************
Special Needs Voter class that extends the Voter class so that it
may Override it's methods.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class SpecialNeedsVoter extends Voter {

    /*****************************************************************
    Getter that returns the booth time of the voter, but multiples it
    by 3.0.
    @return the voter's booth time as a Double object.
    *****************************************************************/
    @Override
    public Double getBoothTime() {
        return boothTime * 3.0;
    }

    /*****************************************************************
    Getter that returns the voter's check in time, but multiples it
    by 1.5.
    @return the voter's check in time as a Double object.
    *****************************************************************/
    @Override
    public Double getCheckInTime() {
        return checkInTime * 1.5;
    }

    /*****************************************************************
    Getter that returns the tolerance time of the voter, but multiples
    it by 2.
    @return the voter's booth time as an int.
    *****************************************************************/
    @Override
    public int getTolerance() {
        return tolerance * 2;
    }
}
