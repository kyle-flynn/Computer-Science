package votingbooth.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingbooth.*;

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

    private Clock clk;
    private BoothLine boothQ;
    private Booth[] booths;

    // Right now, we control the amount of check-in tables
    private CheckInTable tableOne;
    private CheckInTable tableTwo;

    // Data inputs from the top text fields
    private int nextPerson = 0;
    private int avgSecondsCheckIn = 0;
    private int totalTimeSec = 0;
    private int avgSecToVote = 0;
    private int secondsBeforeLeaves = 0;
    private int boothNum = 0;

    // Statistics
    private int throughput;
    private double avgVoteTime;
    private int peopleLeft;
    private int votingLineQ;

    // TODO - Implement the following statistics
    private int peoplePissed;
    private int limitedVoters;
    private int specialVoters;
    private int normalVoters;
    private int avgLimitedVoteTime;
    private int avgSpecialVoteTime;
    private int avgNormalVoteTime;
    private int avgLimitedCheckInTime;
    private int avgSpecialCheckInTime;
    private int avgNormalCheckInTime;

    private VoterProducer produce;

    private boolean started;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        started = false;

        start();
    }

    // EDIT THESE METHODS. THEY SHOULD NEVER BE LESS THAN 0
    private int validateVoterGen(int value) {
        return value;
    }

    private int validateCheckIn(int value) {
        return value;
    }

    private int validateTotalTime(int value) {
        return value;
    }

    private int validateVoteTime(int value) {
        return value;
    }

    private int validateLeaveTime(int value) {
        return value;
    }

    private int validateBoothNum(int value) {
        return value;
    }
    // END METHODS YOU'RE SUPPOSED TO EDIT

    /***********************************************
     * This will link the Start Button to the Logic in the back end
     * For this we are re-assigning the variables to what is
     * stored in the text fields
     * @return void
     ***********************************************/
    @FXML
    public void startSimulation(){

        try {
            nextPerson = validateVoterGen(Integer.parseInt(secondsToNext.getText()));
            avgSecondsCheckIn = validateCheckIn(Integer.parseInt(avgCheckInTime.getText()));
            totalTimeSec = validateTotalTime(Integer.parseInt(totalTime.getText()));
            avgSecToVote = validateVoteTime(Integer.parseInt(avgVotingTime.getText()));
            secondsBeforeLeaves = validateLeaveTime(Integer.parseInt(secondsBeforeLeave.getText()));
            boothNum = validateBoothNum(Integer.parseInt(boothCount.getText()));

            clk = new Clock();
            booths = new Booth[boothNum];

            for (int i = 0; i < boothNum; i++) {
                booths[i] = new Booth(avgSecToVote);
                clk.add(booths[i]);
            }

            boothQ = new BoothLine(booths);
            tableOne = new CheckInTable(avgSecondsCheckIn, boothQ);
            tableTwo = new CheckInTable(avgSecondsCheckIn, boothQ);
            produce = new VoterProducer(booths, nextPerson, secondsBeforeLeaves, (totalTimeSec / nextPerson));

            produce.addTable(tableOne);
            produce.addTable(tableTwo);

            clk.add(produce);
            clk.add(tableOne);
            clk.add(tableTwo);
            clk.add(boothQ);

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
     * @return void
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
        avgTotalVoteTime.clear();
        secondsBeforeLeave.clear();
        boothCount.clear();
    }

    /*****************************************
     * Inherited from animation class, allows
     * the text to change dynamically, useful for
     * the complex GUI
     * @param now
     * @return void
     ****************************************/
	@Override
    public void handle(long now) {
    	if (started) {
    	    calculateStatistics();
            throughPut.setText(throughput + " people with Max = " + (totalTimeSec / nextPerson));
            peopleInLine.setText(peopleLeft + "");
            avgTotalVoteTime.setText(avgVoteTime + " seconds");
            checkInOne.setText(tableOne.getMaxQlength() + "");
            checkInTwo.setText(tableTwo.getMaxQlength() + "");
            votingBoothQ.setText(votingLineQ + "");
        }
    }

    /*******************************************
     * Calculate the statistics and store them for
     * output
     * @return void
     ******************************************/
    private void calculateStatistics() {
        int stillVoting = 0;
        avgVoteTime = 0;
        for (Booth b : booths) {
            throughput = produce.getAllThrougPut();
            if (b.inUse()) {
                stillVoting++;
            }
            peopleLeft = boothQ.getLeft() + tableOne.getVoterQ() + tableTwo.getVoterQ() + stillVoting;
            for (double time : b.getCompletedTimes()) {
                avgVoteTime += time;
            }
            votingLineQ = boothQ.getMaxQlength();
        }
        avgVoteTime = avgVoteTime / (totalTimeSec / nextPerson);
    }

    private void showDataGraphically() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GraphicalData.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();

            newStage.setScene(scene);
            newStage.setTitle("Graphical Data");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
