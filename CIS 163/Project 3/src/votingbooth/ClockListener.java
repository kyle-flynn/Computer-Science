package votingbooth;

/*****************************************************************
Click Listener Interface.
@author Roger Ferguson
@version 1.0
*****************************************************************/
public interface ClockListener {

    /*****************************************************************
    Inheritable Method that handles when a 'tick" of time goes by.
    @param tick Amount of time in simulation.
    *****************************************************************/
    void event(int tick);
}
