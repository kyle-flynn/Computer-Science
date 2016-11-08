package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingbooth.*;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/*****************************************************************
Complex Simulator Controller class. Responsible for linking the
front-end code to the back-end, and run the simulation. This
fulfills the requirements for part 'C' and 'B'.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class BasicSimulatorController implements Initializable {

    /** TextField that contains the time between voter generations. */
    @FXML private TextField secondsToNext;

    /** TextField that contains the avg time to check in. */
    @FXML private TextField avgCheckInTime;

    /** TextField that contains the maximum simulation time. */
    @FXML private TextField totalTime;

    /** TextField that contains the avg time to vote. */
    @FXML private TextField avgVotingTime;

    /** TextField that contains the avg time before voter leaves. */
    @FXML private TextField secondsBeforeLeave;

    /** TextField that contains the number of booths for the sim. */
    @FXML private TextField boothCount;

    /** Statistic that displays the number of completed voters. */
    @FXML private TextField throughPut;

    /** Statistic that displays the amount of people left in the sim. */
    @FXML private TextField peopleInLine;

    /** Statistic that displays the avg time to vote for voters. */
    @FXML private TextField voterVoteTime;

    /** Statistic that displays the avg check in time for table one. */
    @FXML private TextField checkInOne;

    /** Statistic that displays the avg check in time for table two. */
    @FXML private TextField checkInTwo;

    /** Statistic that displays the record for people in line. */
    @FXML private TextField votingBoothQ;

    /** Master clock instance */
    private Clock clk;

    /** Instance of the BoothLine that stores voters. */
    private BoothLine boothQ;

    /** Array that contains the number of Booth objects in the sim.  */
    private Booth[] booths;

    /** Checkin Table for last names A-L */
    private CheckInTable tableOne;

    /** Checkin Table for last names M-Z */
    private CheckInTable tableTwo;

    /** Main VoterProducer object that produces voters. */
    private VoterProducer produce;

    /** LinkedList of CheckInTable objects. We use LinkedList because it
    is better for adding/subtracting data. We will not be modifying. */
    private LinkedList<CheckInTable> tables;

    /** Obtained from text field - time between voters */
    private int nextPerson;

    /** Obtained from text field - avg time to check in. */
    private int avgSecondsCheckIn;

    /** Obtained from text field - maximum simulation time. */
    private int totalTimeSec;

    /** Obtained from text field - avg time to vote. */
    private int avgSecToVote;

    /** Obtained from text field - avg time before voter leaves. */
    private int secondsBeforeLeaves;

    /** Obtained from text field - number of booths for the sim. */
    private int boothNum;

    /** Statistic that gets number of completed voters. */
    private int throughput;

    /** Statistic that gets the avg time to vote for voters. */
    private double avgVoteTime;

    /** Statistic that gets the amount of people left in the sim. */
    private int peopleLeft;

    /** Statistic that gets the amount of people left in the boothQ. */
    private int votingLineQ;

    /** Statistic that gets the amount of people still in a booth. */
    private int stillVoting;

    /** Statistic for normal voters who left the sim. */
    private int normalPissed;

    /** Statistic for limited time voters who left the sim. */
    private int limitedPissed;

    /** Statistic for special needs voters who left the sim. */
    private int specialPissed;

    /** Statistic for super special needs voters who left the sim. */
    private int superPissed;

    /** Statistic for total voters who left the sim. */
    private int totalPissed;

    /** Statistic for normal voters who successfully voted. */
    private int normalVoted;

    /** Statistic for limited time voters who successfully voted. */
    private int limitedVoted;

    /** Statistic for special needs voters who successfully voted. */
    private int specialVoted;

    /** Statistic for super special needs voters who successfully voted. */
    private int superVoted;

    /** Statistic for total voters who successfully voted. */
    private int totalVoted;

    /** Statistic for limited time voters avg time to vote. */
    private int avgLimitedVoteTime;

    /** Statistic for special needs voters avg time to vote. */
    private int avgSpecialVoteTime;

    /** Statistic for normal voters avg time to vote. */
    private int avgNormalVoteTime;

    /** Statistic for super special needs voters avg time to vote. */
    private int avgSuperVoteTime;

    /** Statistic for total voters avg time to vote. */
    private int avgTotalVoteTime;

    /** Statistic for limited time voters avg check in time. */
    private double avgLimitedCheckInTime;

    /** Statistic for special needs voters avg check in time. */
    private double avgSpecialCheckInTime;

    /** Statistic for super special needs voters avg check in time. */
    private double avgSuperCheckInTime;

    /** Statistic for normal voters avg check in time. */
    private double avgNormalCheckInTime;

    /** Statistic for total voters avg check in time. */
    private double avgTotalCheckInTime;

    /** Statistic for normal voters total time in the sim. */
    private double normalComplete;

    /** Statistic for limited time voters total time in the sim. */
    private double limitedComplete;

    /** Statistic for special needs voters total time in the sim. */
    private double specialComplete;

    /** Statistic for super special needs voters total time in the sim. */
    private double superComplete;

    /** Statistic for total voters total time in the sim. */
    private double totalComplete;

    /*****************************************************************
    Overriden method that occurs when the application has loaded, and
    is ready to run code.
    @param location The location of the application. Handled by JavaFX.
    @param resources The ResourceBundle of the application. Handled by
    JavaFX.
    *****************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Initializing our declared variables. */
        nextPerson = 0;
        avgSecondsCheckIn = 0;
        totalTimeSec = 0;
        avgSecToVote = 0;
        secondsBeforeLeaves = 0;
        boothNum = 0;
    }

    /*****************************************************************
    Method that runs whenever the run button is pressed (handled in FX)
    and completely runs the simulation.
    *****************************************************************/
    @FXML
    public void startSimulation(){

        try {
            nextPerson = (Integer.parseInt(secondsToNext.getText()));
            avgSecondsCheckIn = (Integer.parseInt(avgCheckInTime.getText()));
            totalTimeSec = (Integer.parseInt(totalTime.getText()));
            avgSecToVote = (Integer.parseInt(avgVotingTime.getText()));
            secondsBeforeLeaves = (Integer.parseInt(secondsBeforeLeave.getText()));
            boothNum = (Integer.parseInt(boothCount.getText()));

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

            tables = new LinkedList<>();

            tables.add(tableOne);
            tables.add(tableTwo);

            produce.addTable(tableOne);
            produce.addTable(tableTwo);

            clk.add(produce);
            clk.add(tableOne);
            clk.add(tableTwo);
            clk.add(boothQ);

            clk.start(totalTimeSec);

            resetStatistics();
            calculateStatistics();
            addStatistics();
            outputInformation();
        } catch (NumberFormatException nfe) {
            System.out.println("Can't convert.");
        }
    }

    /*****************************************************************
    Method that runs whenever the quit simulation button is pressed.
    This simply exits the application.
    *****************************************************************/
    @FXML
    public void quitSimulation() {
        System.exit(0);
    }

    /*****************************************************************
    Private helper method that sets the TextField's text to the basic
    statistics required for part 'C'.
    *****************************************************************/
    private void outputInformation() {
        throughPut.setText(Statistics.getStatistic("throughput") + " people with Max = " + Statistics.getStatistic("maxVoters"));
        peopleInLine.setText(Statistics.getStatistic("peopleLeft") + "");
        voterVoteTime.setText(Statistics.getStatistic("avgVoteTime") + " seconds");
        checkInOne.setText(tableOne.getMaxQlength() + "");
        checkInTwo.setText(tableTwo.getMaxQlength() + "");
        votingBoothQ.setText(Statistics.getStatistic("votingLineQ") + "");
	}

    /*****************************************************************
     Private helper method that calculates all of our necessary
     statistics.
     *****************************************************************/
    private void calculateStatistics() {
        for (Booth b : booths) {
            if (b.inUse()) {
                stillVoting++;
            }
            for (double time : b.getCompletedTimes()) {
                avgVoteTime += time;
            }
        }

        /* We check which statistic to calculate for each type of
        voter by checking which voter is an instanceof the voter
        we're looking for. Most of it is self-explanatory. */
        for (Voter v : produce.getVoters()) {
            if (v instanceof LimitedTimeVoter) {
                if (v.isPissed()) {
                    limitedPissed++;
                }
                if (v.hasVoted()) {
                    limitedVoted++;
                    avgLimitedVoteTime += v.getBoothTime();
                    limitedComplete += v.getTimeSpent();
                }
                if (v.hasCheckedIn()) {
                    avgLimitedCheckInTime+= v.getCheckInTime();
                }
            } else if (v instanceof SpecialNeedsVoter) {
                if (v instanceof SuperSpecialNeedsVoter) {
                    if (v.isPissed()) {
                        superPissed++;
                    }
                    if (v.hasVoted()) {
                        superVoted++;
                        avgSuperVoteTime+= v.getBoothTime();
                        superComplete += v.getTimeSpent();
                    }
                    if (v.hasCheckedIn()) {
                        avgSuperCheckInTime+= v.getCheckInTime();
                    }
                } else {
                    if (v.isPissed()) {
                        specialPissed++;
                    }
                    if (v.hasVoted()) {
                        specialVoted++;
                        avgSpecialVoteTime+= v.getBoothTime();
                        specialComplete += v.getTimeSpent();
                    }
                    if (v.hasCheckedIn()) {
                        avgSpecialCheckInTime+= v.getCheckInTime();
                    }
                }
            } else {
                if (v.isPissed()) {
                    normalPissed++;
                }
                if (v.hasVoted()) {
                    normalVoted++;
                    avgNormalVoteTime+= v.getBoothTime();
                    normalComplete += v.getTimeSpent();
                }
                if (v.hasCheckedIn()) {
                    avgNormalCheckInTime+= v.getCheckInTime();
                }
            }
        }
        for (CheckInTable table : tables) {
            peopleLeft+= table.getVoterQ();
        }

        avgTotalCheckInTime = (avgNormalCheckInTime
                + avgLimitedCheckInTime
                + avgSpecialCheckInTime
                + avgSuperCheckInTime)
                / produce.getVoters().size();
        totalPissed = normalPissed + limitedPissed +
                specialPissed + superPissed;
        totalVoted = normalVoted + limitedVoted +
                specialVoted + superVoted;
        votingLineQ = boothQ.getMaxQlength();
        throughput = produce.getAllThrougPut();
        peopleLeft += boothQ.getLeft() + stillVoting;
        if (totalVoted != 0) {
            avgVoteTime = avgVoteTime / totalVoted;
            avgTotalVoteTime = (avgLimitedVoteTime +
                    avgSpecialVoteTime +
                    avgNormalVoteTime +
                    avgSuperVoteTime) /
                    totalVoted;
            totalComplete = (limitedComplete +
                    specialComplete +
                    normalComplete +
                    superComplete) / totalVoted;
        } else {
            avgVoteTime = 0;
        }
        if (limitedVoted != 0) {
            avgLimitedVoteTime = avgLimitedVoteTime / limitedVoted;
            limitedComplete = limitedComplete / limitedVoted;
        } else {
            avgLimitedVoteTime = 0;
        }
        if (specialVoted != 0) {
            avgSpecialVoteTime = avgSpecialVoteTime / specialVoted;
            specialComplete = specialComplete / specialVoted;
        } else {
            avgSpecialVoteTime = 0;
        }
        if (normalVoted != 0) {
            avgNormalVoteTime = avgNormalVoteTime / normalVoted;
            normalComplete = normalComplete / normalVoted;
        } else {
            avgNormalVoteTime = 0;
        }
        if (superVoted != 0) {
            avgSuperVoteTime = avgSuperVoteTime / superVoted;
            superComplete = superComplete / superVoted;
        } else {
            avgSuperVoteTime = 0;
        }
    }

    /*****************************************************************
     Private helper method that adds all of our statistics to the stats
     class. This is so that our other applications can use this data
     as they please.
     *****************************************************************/
    private void addStatistics() {

        /* Clear the stats HashMap first. */
        Statistics.clearStatistics();

        /* Now adding all of our statistics using a key/value. */
        Statistics.addStatistic("avgVoteTime", avgVoteTime);
        Statistics.addStatistic("votingLineQ", votingLineQ);
        Statistics.addStatistic("throughput", throughput);
        Statistics.addStatistic("peopleLeft", peopleLeft);
        Statistics.addStatistic("avgVoteTime", avgVoteTime);
        Statistics.addStatistic("avgNormalVoteTime", avgNormalVoteTime);
        Statistics.addStatistic("avgLimitedVoteTime", avgLimitedVoteTime);
        Statistics.addStatistic("avgSpecialVoteTime", avgSpecialVoteTime);
        Statistics.addStatistic("avgSuperVoteTime", avgSuperVoteTime);
        Statistics.addStatistic("avgTotalVoteTime", avgTotalVoteTime);
        Statistics.addStatistic("maxVoters", (totalTimeSec / nextPerson));
        Statistics.addStatistic("normalTotal", produce.getNormalVoters().size());
        Statistics.addStatistic("limitedTotal", produce.getLimitedVoters().size());
        Statistics.addStatistic("specialTotal", produce.getSpecialVoters().size());
        Statistics.addStatistic("superTotal", produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("totalTotal", produce.getNormalVoters().size() +
                produce.getLimitedVoters().size() +
                produce.getSpecialVoters().size() +
                produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("normalVoted", normalVoted);
        Statistics.addStatistic("limitedVoted", limitedVoted);
        Statistics.addStatistic("specialVoted", specialVoted);
        Statistics.addStatistic("superVoted", superVoted);
        Statistics.addStatistic("totalVoted", totalVoted);
        Statistics.addStatistic("normalPissed", normalPissed);
        Statistics.addStatistic("limitedPissed", limitedPissed);
        Statistics.addStatistic("specialPissed", specialPissed);
        Statistics.addStatistic("superPissed", superPissed);
        Statistics.addStatistic("totalPissed", totalPissed);
        Statistics.addStatistic("normalCheckIn", avgNormalCheckInTime
                / produce.getNormalVoters().size());
        Statistics.addStatistic("limitedCheckIn", avgLimitedCheckInTime
                / produce.getLimitedVoters().size());
        Statistics.addStatistic("specialCheckIn", avgSpecialCheckInTime
                / produce.getSpecialVoters().size());
        Statistics.addStatistic("superCheckIn", avgSuperCheckInTime
                / produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("totalCheckIn", avgTotalCheckInTime);
        Statistics.addStatistic("normalComplete", normalComplete);
        Statistics.addStatistic("limitedComplete", limitedComplete);
        Statistics.addStatistic("specialComplete", specialComplete);
        Statistics.addStatistic("superComplete", superComplete);
        Statistics.addStatistic("totalComplete", totalComplete);
        Statistics.addStatistic("booths", booths);
        Statistics.addStatistic("tables", tables);
        Statistics.addStatistic("leftInLine", boothQ.getLeft());
    }

    /*****************************************************************
    Private helper method that resets the class-specific statistic
    variables. This is to assist in stat-bleeding where statistics
    blend into each other or keep appending when they should be reset.
    *****************************************************************/
    private void resetStatistics() {
        stillVoting = 0;
        avgVoteTime = 0;
        normalPissed = 0;
        limitedPissed = 0;
        specialPissed = 0;
        superPissed = 0;
        totalPissed = 0;
        normalVoted = 0;
        limitedVoted = 0;
        specialVoted = 0;
        superVoted = 0;
        totalVoted = 0;
        avgNormalCheckInTime = 0;
        avgLimitedCheckInTime = 0;
        avgSpecialCheckInTime = 0;
        avgTotalCheckInTime = 0;
        avgSuperCheckInTime = 0;
        avgNormalVoteTime = 0;
        avgLimitedVoteTime = 0;
        avgSpecialVoteTime = 0;
        avgSuperVoteTime = 0;
        avgTotalVoteTime = 0;
        peopleLeft = 0;
    }

    /*****************************************************************
    Method that runs whenever the show more data button is pressed. It
    opens a new application and loads the statistics from this run of
    the simulation.
    @throws Exception Thrown if the FXML file cannot be found, or there
    was an error producing the application.
    *****************************************************************/
    @FXML
    public void showMoreData() {
        try {

            /* This is the main 'parent' element of the new
            application. */
            Parent root = FXMLLoader.load(
                    getClass().getResource("/GraphicalData.fxml"));

            /* This is responsible for containing the current scene of
            the new application. */
            Scene scene = new Scene(root);

            /* The new stage required for the new application. */
            Stage newStage = new Stage();

            /* Setting the stage properties. */
            newStage.setScene(scene);
            newStage.setTitle("WOW SO MANY STATS MAN");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
