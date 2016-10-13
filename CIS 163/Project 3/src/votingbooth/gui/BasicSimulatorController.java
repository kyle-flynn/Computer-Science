package votingbooth.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import votingbooth.Booth;
import votingbooth.Clock;
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

    private Button startSim;
    private Button stopSim;

    private Clock clk;
    private Booth booth;

    private int numOfTicksNextPerson = 20;
    private int averageBoothTime = 20;

    private VoterProducer produce;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clk = new Clock();
        booth = new Booth();
        produce = new VoterProducer(booth, 20, 18);

        clk.add(produce);
        clk.add(booth);

        clk.run(10000);

        start();
    }

    @Override
    public void handle(long now) {

    }
}
