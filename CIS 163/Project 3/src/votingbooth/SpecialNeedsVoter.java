package votingbooth;

/**
 * Created by Jarred on 10/17/16.
 */

public class SpecialNeedsVoter extends Voter {

    public Double getBoothTime() {
        return boothTime * 3.0;
    }

    public Double getCheckInTime() {
        return checkInTime * 1.5;
    }

    public int getTolerance() {
        return tolerance * 2;
    }

}
