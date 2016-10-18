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

    // Top-half of the text field variables
    @FXML private TextField secondsToNext;
    @FXML private TextField avgCheckInTime;
    @FXML private TextField totalTime;
    @FXML private TextField avgVotingTime;
    @FXML private TextField secondsBeforeLeave;
    @FXML private TextField boothCount;

    // Bottom-half of the text field variables
    @FXML private TextField throughPut;
    @FXML private TextField peopleInLine;
    @FXML private TextField avgTotalVoteTime;
    @FXML private TextField checkInOne;
    @FXML private TextField checkInTwo;
    @FXML private TextField votingBoothQ;

    // Middle parts of the variables
    @FXML private Button startButton;
    @FXML private Button quitButton;

    private ClockTimer clk;
    private Booth[] booths;

    // Data inputs from the top text fields
    private int nextPerson = 0;
    private int avgSecondsCheckIn = 0;
    private int totalTimeSec = 0;
    private int avgSecToVote = 0;
    private int secondsBeforeLeaves = 0;
    private int boothNum = 0;

    // Data output variables
    private int throughput;
    private int avgVoteTime;
    private int peopleLeft;
    private int checkInOneLeft;
    private int checkInTwoLeft;
    private int votingLineQ;

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
     * stored in the text fields
     * @return void
     ***********************************************/
    @FXML
    public void startSimulation(){

        try {
            nextPerson = Integer.parseInt(secondsToNext.getText());
            avgSecondsCheckIn = Integer.parseInt(avgCheckInTime.getText());
            totalTimeSec = Integer.parseInt(totalTime.getText());
            avgSecToVote = Integer.parseInt(avgVotingTime.getText());
            secondsBeforeLeaves = Integer.parseInt(secondsBeforeLeave.getText());
            boothNum = Integer.parseInt(boothCount.getText());

            clk = new ClockTimer();
            booths = new Booth[boothNum];
            produce = new VoterProducer(booths, nextPerson, avgSecToVote);

            clk.add(produce);

            for (Booth b : booths) {
                clk.add(b);
            }

            clk.run(totalTimeSec);

            outputInformation();
        } catch (NumberFormatException nfe) {
            System.out.println("Can't convert.");
        }
    }

    /*********************************************
     * Quits the simulator, this is linked to the FXML GUI button
     * @return void
     *********************************************/
    @FXML
    private void quitSimulation() {
        System.exit(0);
    }

    /**********************************************
     * Prints the output information
     * @return void
     *********************************************/
    private void outputInformation() {
    	started = true;
	}

    /********************************************
     * Set up file menus
     *******************************************/
    @FXML
	private void setupMenus(){

    }

    /*******************************************
     * Resets everything in the GUI for different
     * simulations, this will be accessed via the
     * file Menu
     * @return void
     ******************************************/
    @FXML
    private void reset(){
        secondsToNext.clear();
        avgCheckInTime.clear();
        totalTime.clear();
        avgVotingTime.clear();
        secondsBeforeLeave.clear();
        boothCount.clear();
    }

	@Override
    public void handle(long now) {
    	if (started) {
    	    calculateStatistics();
            throughPut.setText(throughput + " people with Max = " + (totalTimeSec / nextPerson));
            peopleInLine.setText(peopleLeft + "");
            avgVotingTime.setText(avgVoteTime + "");
            checkInOne.setText("");
            checkInTwo.setText("");
            votingBoothQ.setText(votingLineQ + "");
        }
    }

    private void calculateStatistics() {
        for (Booth b : booths) {
            // TODO - Append all booth statistics,
            // and then calculate average for votingTime
            throughput += b.getThroughPut();
            peopleLeft += b.getLeft();
            avgVoteTime += (b.getThroughPut() / totalTimeSec);
            votingLineQ += b.getMaxQlength();
        }
    }

    /**************************************************
     * Methods to do the Input Information
     * 
     **************************************************/
    
}
