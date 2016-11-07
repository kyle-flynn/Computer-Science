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

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by kylef_000 on 10/31/2016.
 */
public class ComplexSimulatorController implements Initializable {

    @FXML private TextField voterGen;
    @FXML private TextField voterCheckIn;
    @FXML private TextField voterVoting;
    @FXML private TextField voterLeave;
    @FXML private TextField maxSimulationTime;
    @FXML private TextField curSimulationTime;
    @FXML private TextField simulationTime;
    @FXML private Label checkInText;
    @FXML private Label boothText;
    @FXML private Button outputBtn;
    @FXML private Button statisticsBtn;
    @FXML private Button runBtn;

    private int voterGenTime;
    private int avgVoterCheckIn;
    private int avgVoterVoting;
    private int avgVoterTolerance;
    private int maxTime;
    private int curTime;
    private int timeToAdd;

    private Clock clk;
    private VoterProducer producer;
    private BoothLine boothQ;
    private LinkedList<Booth> booths;
    private LinkedList<CheckInTable> tables;

    // Stats
    // Statistics
    private int throughput;
    private double avgVoteTime;
    private int peopleLeft;
    private int votingLineQ;
    private int stillVoting;

    private int normalPissed;
    private int limitedPissed;
    private int specialPissed;
    private int superPissed;
    private int totalPissed;
    private int normalVoted;
    private int limitedVoted;
    private int specialVoted;
    private int superVoted;
    private int totalVoted;
    private int avgLimitedVoteTime;
    private int avgSpecialVoteTime;
    private int avgNormalVoteTime;
    private int avgSuperVoteTime;
    private int avgTotalVoteTime;
    private double avgLimitedCheckInTime;
    private double avgSpecialCheckInTime;
    private double avgSuperCheckInTime;
    private double avgNormalCheckInTime;
    private double avgTotalCheckInTime;
    private double normalComplete;
    private double limitedComplete;
    private double specialComplete;
    private double superComplete;
    private double totalComplete;

    private boolean memoryCleared;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        memoryCleared = true;

        outputBtn.setDisable(true);
        statisticsBtn.setDisable(true);
        runBtn.setDisable(false);
    }

    private Booth[] getBoothsAsArray() {
        Booth[] tempBooths = new Booth[booths.size()];
        for (int i = 0; i < tempBooths.length; i++) {
            tempBooths[i] = booths.get(i);
        }
        return tempBooths;
    }

    @FXML
    public void runSimulation() {
        if (validInputs() && memoryCleared) {
            boothQ.setBooths(getBoothsAsArray());
            producer = new VoterProducer(getBoothsAsArray(), voterGenTime, avgVoterTolerance, (maxTime / voterGenTime));

            for (Booth booth : booths) {
                booth.setAverageBoothTime(avgVoterVoting);
                clk.add(booth);
            }

            for (CheckInTable table : tables) {
                table.setAvgCheckInTime(avgVoterCheckIn);
                producer.addTable(table);
                clk.add(table);
            }

            clk.add(producer);
            clk.add(boothQ);

            clk.start(maxTime);

            calculateStatistics();
            addStatistics();

            memoryCleared = false;

            outputBtn.setDisable(false);
            statisticsBtn.setDisable(false);
            runBtn.setDisable(true);
        } else {
            // Show error dialog!
        }
    }

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

    @FXML
    public void showOutput() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ComplexOutput.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();

            newStage.setScene(scene);
            newStage.setTitle("Simulation Output");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showStatistics() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GraphicalData.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();

            newStage.setScene(scene);
            newStage.setTitle("More Data");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addTime() {
        if (validTime()) {
            if (validInputs()) {
                if ((curTime + timeToAdd) <= maxTime) {
                    boothQ.setBooths(getBoothsAsArray());

                    if (producer == null) {
                        producer = new VoterProducer(getBoothsAsArray(), voterGenTime, avgVoterTolerance, (maxTime / voterGenTime));
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

                    outputBtn.setDisable(false);
                    statisticsBtn.setDisable(false);

                    curTime += timeToAdd;
                    curSimulationTime.setText(curTime + "");
                } else {
                    // Show error dialog!
                }
            }
        } else {
            // Show error dialog!
        }
    }

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
            // Show error dialog!
        }
    }

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
            // Show error dialog!
        }
    }

    @FXML
    public void removeBooth() {
        if (booths.size() > 1) {
            booths.removeLast();
            boothText.setText("Number of Booths (" + booths.size() + ")");
        } else {
            // Show error dialog!
        }
    }

    @FXML
    public void removeTable() {
        if (tables.size() > 1) {
            tables.removeLast();
            checkInText.setText("Number of Check In Tables (" + tables.size() + ")");
        }
    }

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

    private boolean validTime() {
        try {
            timeToAdd = Integer.parseInt(simulationTime.getText());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private boolean validBoothTime() {
        try {
            avgVoterVoting = Integer.parseInt(voterVoting.getText());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private boolean validCheckInTime() {
        try {
            avgVoterCheckIn = Integer.parseInt(voterCheckIn.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void calculateStatistics() {
        for (Booth b : booths) {
            if (b.inUse()) {
                stillVoting++;
            }
            for (double time : b.getCompletedTimes()) {
                avgVoteTime += time;
            }
        }
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

        avgTotalCheckInTime = (avgNormalCheckInTime + avgLimitedCheckInTime + avgSpecialCheckInTime + avgSuperCheckInTime) / producer.getVoters().size();
        totalPissed = normalPissed + limitedPissed + specialPissed + superPissed;
        totalVoted = normalVoted + limitedVoted + specialVoted + superVoted;
        votingLineQ = boothQ.getMaxQlength();
        throughput = producer.getAllThrougPut();
        peopleLeft += boothQ.getLeft() + stillVoting;
        if (totalVoted != 0) {
            avgVoteTime = avgVoteTime / totalVoted;
            avgTotalVoteTime = (avgLimitedVoteTime + avgSpecialVoteTime + avgNormalVoteTime + avgSuperVoteTime) / totalVoted;
            totalComplete = (limitedComplete + specialComplete + normalComplete + superComplete) / totalVoted;
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

    private void addStatistics() {
        Statistics.clearStatistics();
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
        Statistics.addStatistic("totalTotal", producer.getNormalVoters().size() + producer.getLimitedVoters().size() + producer.getSpecialVoters().size() + producer.getSuperSpecialVoters().size());
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
        Statistics.addStatistic("normalCheckIn", avgNormalCheckInTime / producer.getNormalVoters().size());
        Statistics.addStatistic("limitedCheckIn", avgLimitedCheckInTime / producer.getLimitedVoters().size());
        Statistics.addStatistic("specialCheckIn", avgSpecialCheckInTime / producer.getSpecialVoters().size());
        Statistics.addStatistic("superCheckIn", avgSuperCheckInTime / producer.getSuperSpecialVoters().size());
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