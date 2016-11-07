package votingbooth;

/*****************************************************************
 Voter Status Enum Class. Responsible for checking the current state
 of the voter. This makes statistic tracking and voter management
 easier.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public enum VoterStatus {
    CHECKING_IN,
    WAITING_FOR_BOOTH,
    VOTING,
    DONE
}
