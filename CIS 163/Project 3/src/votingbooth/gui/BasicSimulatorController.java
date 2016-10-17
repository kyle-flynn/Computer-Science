package votingbooth.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import votingbooth.Booth;
import votingbooth.Clock;
import votingbooth.ClockTimer;
import votingbooth.VoterProducer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kylef_000 on 10/12/2016.
 */
public class BasicSimulatorController extends AnimationTimer implements Initializable {

    @FXML private TextField secondsToNext;
    @FXML private TextField avgCheckInTime;
    @FXML private TextField totalTime;
    @FXML private TextField avgVotingTime;
    @FXML private TextField secondsBeforeLeave;
    @FXML private TextField boothCount;

    @FXML private TextField throughPut;
    @FXML private TextField peopleInLine;

    @FXML private Button startButton;
    @FXML private Button quitButton;

    private Button startSim;
    private Button stopSim;

    private ClockTimer clk;
    private Booth booth;

    private int numOfTicksNextPerson = 20;
    private int averageBoothTime = 20;
    
    private int nextPerson = 0;
    private int avgSecondsCheckIn = 0;
    private int totalTimeSec = 0;
    private int avgSecondsVoting = 0;
    private int secondsBeforeLeaves = 0;
    private int booths = 0;
    private int throughput = 0;
    
    private VoterProducer produce;

    private boolean started;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        started = false;

        start();
    }


    /***********************************************
     * This will link the Start Button to the Logic in the back end
     * For this we are re-assigning the variables to what is
     * stored in the text fields -JP
     * @return void
     ***********************************************/
    @FXML
    public void startSimulation(){
        try {
            nextPerson = Integer.parseInt(secondsToNext.getText());
            avgSecondsCheckIn = Integer.parseInt(avgCheckInTime.getText());
            totalTimeSec = Integer.parseInt(totalTime.getText());
            avgSecondsVoting = Integer.parseInt(avgVotingTime.getText());
            secondsBeforeLeaves = Integer.parseInt(secondsBeforeLeave.getText());
            booths = Integer.parseInt(boothCount.getText());

            clk = new ClockTimer();
            booth = new Booth();
            produce = new VoterProducer(booth, nextPerson, avgSecondsVoting);

            clk.add(produce);
            clk.add(booth);

            clk.run(totalTimeSec);

            outputInformation();
        } catch (NumberFormatException nfe) {
            System.out.println("Can't convert.");
        }
    }

    @FXML
    private void quitSimulation() {
        System.exit(0);
    }

    /**********************************************
     * Prints the output information
     * @return void
     *********************************************/
    private void outputInformation() {
		//Print throughput
    	/**
    	 * Total number of people passing through the voting booths, I feel like this
    	 * is calculated from the other variables that the user enters
    	 */
		
    	started = true;
	}
	@Override
    public void handle(long now) {
    	if (started) {
            throughPut.setText(booth.getThroughPut() + " people with Max = " + (totalTimeSec / nextPerson));
            peopleInLine.setText("" + booth.getLeft());
        }
    }
    
    /**************************************************
     * Methods to do the Input Information
     * 
     **************************************************/
    
}
