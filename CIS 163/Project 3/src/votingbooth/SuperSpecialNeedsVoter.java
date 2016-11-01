package votingbooth;

/**
 * Created by Kyle Flynn on 10/31/2016.
 */
public class SuperSpecialNeedsVoter extends SpecialNeedsVoter {

    public Double getBoothTime() {
        return boothTime * 4.0;
    }

    public Double getCheckInTime() {
        return checkInTime * 2.0;
    }

    public int getTolerance() {
        return tolerance * 3;
    }

}
