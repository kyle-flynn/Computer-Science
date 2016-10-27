package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import votingbooth.Statistics;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kylef_000 on 10/26/2016.
 */
public class SimpleSimulatorController implements Initializable {

    // Output variables
    @FXML private TextField normalTotal;
    @FXML private TextField limitedTotal;
    @FXML private TextField specialTotal;
    @FXML private TextField totalTotal;
    @FXML private TextField normalVoted;
    @FXML private TextField limitedVoted;
    @FXML private TextField specialVoted;
    @FXML private TextField totalVoted;
    @FXML private TextField normalPissed;
    @FXML private TextField limitedPissed;
    @FXML private TextField specialPissed;
    @FXML private TextField totalPissed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStatistics();
    }

    private void getStatistics() {
        normalTotal.setText(Statistics.getStatistic("normalTotal") + "");
        limitedTotal.setText(Statistics.getStatistic("limitedTotal") + "");
        specialTotal.setText(Statistics.getStatistic("specialTotal") + "");
        totalTotal.setText(Statistics.getStatistic("totalTotal") + "");
        normalVoted.setText(Statistics.getStatistic("normalVoted") + "");
        limitedVoted.setText(Statistics.getStatistic("limitedVoted") + "");
        specialVoted.setText(Statistics.getStatistic("specialVoted") + "");
        totalVoted.setText(Statistics.getStatistic("totalVoted") + "");
        normalPissed.setText(Statistics.getStatistic("normalPissed") + "");
        limitedPissed.setText(Statistics.getStatistic("limitedPissed") + "");
        specialPissed.setText(Statistics.getStatistic("specialPissed") + "");
        totalPissed.setText(Statistics.getStatistic("totalPissed") + "");
    }

}
