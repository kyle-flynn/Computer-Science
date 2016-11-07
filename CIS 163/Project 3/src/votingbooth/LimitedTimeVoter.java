package votingbooth;

/*****************************************************************
Limited Time Voter class that extends the Voter class so that it
may Override it's methods.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class LimitedTimeVoter extends Voter {

    /*****************************************************************
    Getter that returns the booth time of the voter, but multiples it
    by 0.5.
    @return the voter's booth time as a Double object.
    *****************************************************************/
    public Double getBoothTime() {
        return boothTime * 0.5;
    }

}
