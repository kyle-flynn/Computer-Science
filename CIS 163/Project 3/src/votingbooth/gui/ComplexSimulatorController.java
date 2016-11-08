package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingbooth.*;

import javax.swing.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/*****************************************************************
Complex Simulator Controller class. Responsible for linking the
front-end code to the back-end, and run the simulation. This
fulfills the requirements for part 'A'.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class ComplexSimulatorController implements Initializable {

    /** TextField that contains the time between voter generations. */
    @FXML private TextField voterGen;

    /** TextField that contains the avg time to check in.*/
    @FXML private TextField voterCheckIn;

    /** TextField that contains the avg time to vote.*/
    @FXML private TextField voterVoting;

    /** TextField that contains the avg time before voter leaves.*/
    @FXML private TextField voterLeave;

    /** TextField that contains the maximum simulation time. */
    @FXML private TextField maxSimulationTime;

    /** TextField that contains the current simulation time.*/
    @FXML private TextField curSimulationTime;

    /** TextField that contains the simulation time to add. */
    @FXML private TextField simulationTime;

    /** Label that contains the amount of check in tables. */
    @FXML private Label checkInText;

    /** Label that contains the amount of booths. */
    @FXML private Label boothText;

    /** Button that shows he output application. */
    @FXML private Button outputBtn;

    /** Button that shows the statistics application. */
    @FXML private Button statisticsBtn;

    /** Button that runs the simulation. */
    @FXML private Button runBtn;

    /** Obtained from text field - time between voters */
    private int voterGenTime;

    /** Obtained from text field - avg time to check in. */
    private int avgVoterCheckIn;

    /** Obtained from text field - avg time to vote. */
    private int avgVoterVoting;

    /** Obtained from text field - avg time before voter leaves. */
    private int avgVoterTolerance;

    /** Obtained from text field - maximum simulation time. */
    private int maxTime;

    /** Obtained from text field - current simulation time. */
    private int curTime;

    /** Obtained from text field - time to add to simulation. */
    private int timeToAdd;

    /** Master clock instance */
    private Clock clk;

    /** Main VoterProducer object that produces voters. */
    private VoterProducer producer;

    /** Instance of the BoothLine that stores voters. */
    private BoothLine boothQ;

    /** LinkedList of Booth objects. We use LinkedList because it is
    better for adding/subtracting data. We will not be modifying. */
    private LinkedList<Booth> booths;

    /** LinkedList of CheckInTable objects. We use LinkedList because it
    is better for adding/subtracting data. We will not be modifying. */
    private LinkedList<CheckInTable> tables;

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

    /** Controls whether or not the user can see output of the sim. */
    private boolean memoryCleared;


    /*****************************************************************
    Overriden method that occurs when the application has loaded, and
    is ready to run code.
    @param location The location of the application. Handled by JavaFX.
    @param resources The ResourceBundle of the application. Handled by
    JavaFX.
    *****************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Initializing our declared variables */
        booths = new LinkedList<>();
        tables = new LinkedList<>();

        /* Setting default values */
        voterGen.setText("20");
        voterCheckIn.setText("20");
        voterVoting.setText("60");
        voterLeave.setText("900");
        maxSimulationTime.setText("10000");

        clk = new Clock();

        /* What's a voting simulator without a table and a booth? */
        addBooth();
        boothQ = new BoothLine(getBoothsAsArray());
        addTable();

        /* Controls which buttons are grayed out. */
        memoryCleared = true;

        /* Disable our output buttons, but show the run btn. */
        outputBtn.setDisable(true);
        statisticsBtn.setDisable(true);
        runBtn.setDisable(false);
    }

    /*****************************************************************
    Private helper method that converts a List into a single dimensional
    array, so that our classes can read it without conversion issues.
    *****************************************************************/
    private Booth[] getBoothsAsArray() {
        Booth[] tempBooths = new Booth[booths.size()];
        for (int i = 0; i < tempBooths.length; i++) {
            tempBooths[i] = booths.get(i);
        }
        return tempBooths;
    }

    /*****************************************************************
    Method that runs whenever the run button is pressed (handled in FX)
    and completely runs the simulation.
    *****************************************************************/
    @FXML
    public void runSimulation() {

        /* Data must be reset */
        if (validInputs() && memoryCleared) {

            /* Resetting or current variables and their properties */
            boothQ.setBooths(getBoothsAsArray());
            producer = new VoterProducer(getBoothsAsArray(),
                    voterGenTime,
                    avgVoterTolerance,
                    (maxTime / voterGenTime));

            /* If the avg booth time has changed in the gui, we must
            change in in the class logic. */
            for (Booth booth : booths) {
                booth.setAverageBoothTime(avgVoterVoting);
                clk.add(booth);
            }

            /* If the avg check in time has changed in the gui, we must
            change in in the class logic. */
            for (CheckInTable table : tables) {
                table.setAvgCheckInTime(avgVoterCheckIn);
                producer.addTable(table);
                clk.add(table);
            }

            clk.add(producer);
            clk.add(boothQ);

            /* Will run the simulator completely. */
            clk.start(maxTime);

            /* Calculating literally a boat-load of stats... */
            calculateStatistics();
            addStatistics();

            /* Now the memory is not cleared, don't run the simulation
            without clearing the memory pls. */
            memoryCleared = false;

            /* Enable output buttons, but disable the run button. */
            outputBtn.setDisable(false);
            statisticsBtn.setDisable(false);
            runBtn.setDisable(true);
        } else {
            showError("Error validating inputs. Did you fill them out correctly?");
        }
    }

    /*****************************************************************
    Method that runs whenever the clear memory button is pressed. It
    simply resets all of our data and enables us to run the sim again.
    *****************************************************************/
    @FXML
    public void reset() {
        memoryCleared = true;
        curTime = 0;
        curSimulationTime.setText(curTime + "");
        resetStatistics();
        clk.reset();
        outputBtn.setDisable(true);
        statisticsBtn.setDisable(true);
        runBtn.setDisable(false);
    }

    /*****************************************************************
    Method that runs whenever the show output button is pressed. It
    opens a new application and loads the statistics from this run of
    the simulation.
    @throws Exception Thrown if the FXML file cannot be found, or there
    was an error producing the application.
    *****************************************************************/
    @FXML
    public void showOutput() {
        try {

            /* This is the main 'parent' element of the new
            application. */
            Parent root = FXMLLoader.load(
                    getClass().getResource("/ComplexOutput.fxml"));

            /* This is responsible for containing the current scene of
            the new application. */
            Scene scene = new Scene(root);

            /* The new stage required for the new application. */
            Stage newStage = new Stage();

            /* Setting the stage properties. */
            newStage.setScene(scene);
            newStage.setTitle("Simulation Output - WOW SO MANY STATS");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     Method that runs whenever the show stats button is pressed. It
     opens a new application and loads the statistics from this run of
     the simulation.
     @throws Exception Thrown if the FXML file cannot be found, or there
     was an error producing the application.
     *****************************************************************/
    @FXML
    public void showStatistics() {
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

    /*****************************************************************
    Method that runs whenever time is manually added to the simulation.
    This is so that booths and tables may be added/subtracted.
    *****************************************************************/
    @FXML
    public void addTime() {

        /* Unfortunately, the following code must be in the following order. */
        if (validTime()) {
            if (validInputs()) {
                if ((curTime + timeToAdd) <= maxTime) {
                    boothQ.setBooths(getBoothsAsArray());

                    /* If the producer hasn't been initialized by running
                    the simulation, we must create it. Otherwise, just
                    rewrite it's properties. */
                    if (producer == null) {
                        producer = new VoterProducer(
                                getBoothsAsArray(),
                                voterGenTime,
                                avgVoterTolerance,
                                (maxTime / voterGenTime));
                    } else {
                        producer.setBooths(getBoothsAsArray());
                        producer.setTables(tables);
                    }

                    clk.clearListeners();

                    for (Booth booth : booths) {
                        clk.add(booth);
                    }

                    for (CheckInTable table : tables) {
                        clk.add(table);
                    }

                    clk.add(producer);
                    clk.add(boothQ);

                    clk.run(timeToAdd);

                    resetStatistics();
                    calculateStatistics();
                    addStatistics();

                    memoryCleared = false;

                    /* Enable the output buttons, but still be able
                    to run the simulation in it's current state. */
                    outputBtn.setDisable(false);
                    statisticsBtn.setDisable(false);

                    /* This is what makes the simulation still able to be
                    completed. */
                    curTime += timeToAdd;
                    curSimulationTime.setText(curTime + "");
                } else {
                    showError("Error validating inputs. Did you fill them out correctly?");
                }
            }
        } else {
            showError("Your time to add must be valid.");
        }
    }

    /*****************************************************************
    Method that runs whenever the add booth button is pressed. Adds a
    booth to the LinkedList so that the simulation can act accordingly.
    *****************************************************************/
    @FXML
    public void addBooth() {
        if (validBoothTime()) {
            Booth newBooth = new Booth(avgVoterVoting);
            booths.add(newBooth);
            boothText.setText("Number of Booths (" + booths.size() + ")");
            if (booths.size() > 1) {
                boothQ.setBooths(getBoothsAsArray());
            }
        } else {
            showError("Invalid Average Booth Time for Voters.");
        }
    }

    /*****************************************************************
    Method that runs whenever the add table button is pressed. Adds a
    booth to the LinkedList so that the simulation can act accordingly.
    *****************************************************************/
    @FXML
    public void addTable() {
        if (validCheckInTime()) {
            CheckInTable newTable = new CheckInTable(avgVoterCheckIn, boothQ);
            tables.add(newTable);
            checkInText.setText("Number of Check In Tables (" + tables.size() + ")");
            if (producer != null) {
                producer.addTable(newTable);
            }
        } else {
            showError("Invalid Average Check In Time for Voters.");
        }
    }

    /*****************************************************************
    Method that runs whenever the remove booth button is pressed. It
    removes the last booth (thanks LinkedList!) and keeps the others
    so that the simulation can still run.
    *****************************************************************/
    @FXML
    public void removeBooth() {
        if (booths.size() > 1) {
            booths.removeLast();
            boothText.setText("Number of Booths (" + booths.size() + ")");
        } else {
            showError("You must have at least 1 Booth");
        }
    }

    /*****************************************************************
    Method that runs whenever the remove table button is pressed. It
    removes the last table (thanks LinkedList!) and keeps the others
    so that the simulation can still run.
    *****************************************************************/
    @FXML
    public void removeTable() {
        if (tables.size() > 1) {
            tables.removeLast();
            checkInText.setText("Number of Check In Tables (" + tables.size() + ")");
        } else {
            showError("You must have at least 1 CheckInTable");
        }
    }

    /*****************************************************************
    Private helper method that checks to see if all of the text fields
    have valid input. It does assign the variables, which is what
    checks the validation.
    @return true if the text fields can be parsed as in int, and false
    if there was a NumberFormatException.
    *****************************************************************/
    private boolean validInputs() {
        try {
            voterGenTime = Integer.parseInt(voterGen.getText());
            avgVoterTolerance = Integer.parseInt(voterLeave.getText());
            maxTime = Integer.parseInt(maxSimulationTime.getText());
            if (booths.size() < 1 || tables.size() < 1) {
                return false;
            }
            if (!validBoothTime() || !validCheckInTime()) {
                return false;
            }
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /*****************************************************************
    Private helper method that checks to see if the add time value is
    valid. This is separate so that it's easier to individually check
    TextField inputs.
    @return true if the time can be parsed to an int, and false if a
    NumberFormatException is thrown.
    *****************************************************************/
    private boolean validTime() {
        try {
            timeToAdd = Integer.parseInt(simulationTime.getText());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /*****************************************************************
    Private helper method that checks to see if the booth time value is
    valid. This is separate so that it's easier to individually check
    TextField inputs.
     @return true if the booth can be parsed to an int, and false if a
     NumberFormatException is thrown.
    *****************************************************************/
    private boolean validBoothTime() {
        try {
            avgVoterVoting = Integer.parseInt(voterVoting.getText());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /*****************************************************************
    Private helper method that checks to see if the check in time value is
    valid. This is separate so that it's easier to individually check
    TextField inputs.
    @return true if the check in can be parsed to an int, and false if a
    NumberFormatException is thrown.
    *****************************************************************/
    private boolean validCheckInTime() {
        try {
            avgVoterCheckIn = Integer.parseInt(voterCheckIn.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*****************************************************************
    Private helper method that simply shows a JOptionPane dialog to
    display an error with a specific message.
    @param message The message to display to the user.
    *****************************************************************/
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
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
        for (Voter v : producer.getVoters()) {
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
                / producer.getVoters().size();
        totalPissed = normalPissed + limitedPissed +
                specialPissed + superPissed;
        totalVoted = normalVoted + limitedVoted +
                specialVoted + superVoted;
        votingLineQ = boothQ.getMaxQlength();
        throughput = producer.getAllThrougPut();
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
        Statistics.addStatistic("maxVoters", (maxTime / voterGenTime));
        Statistics.addStatistic("normalTotal", producer.getNormalVoters().size());
        Statistics.addStatistic("limitedTotal", producer.getLimitedVoters().size());
        Statistics.addStatistic("specialTotal", producer.getSpecialVoters().size());
        Statistics.addStatistic("superTotal", producer.getSuperSpecialVoters().size());
        Statistics.addStatistic("totalTotal", producer.getNormalVoters().size() +
                producer.getLimitedVoters().size() +
                producer.getSpecialVoters().size() +
                producer.getSuperSpecialVoters().size());
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
                / producer.getNormalVoters().size());
        Statistics.addStatistic("limitedCheckIn", avgLimitedCheckInTime
                / producer.getLimitedVoters().size());
        Statistics.addStatistic("specialCheckIn", avgSpecialCheckInTime
                / producer.getSpecialVoters().size());
        Statistics.addStatistic("superCheckIn", avgSuperCheckInTime
                / producer.getSuperSpecialVoters().size());
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

}