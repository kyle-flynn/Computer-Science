package votingbooth;

/**
 * Created by Jarred on 10/17/16.
 */
public class LimitedTimeVoter extends Voter {

    public Double getBoothTime() {
        return boothTime * 0.5;
    }

}
