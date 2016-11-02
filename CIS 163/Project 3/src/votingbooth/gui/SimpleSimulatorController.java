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
    @FXML private TextField superTotal;
    @FXML private TextField totalTotal;
    @FXML private TextField normalVoted;
    @FXML private TextField limitedVoted;
    @FXML private TextField specialVoted;
    @FXML private TextField superVoted;
    @FXML private TextField totalVoted;
    @FXML private TextField normalPissed;
    @FXML private TextField limitedPissed;
    @FXML private TextField specialPissed;
    @FXML private TextField superPissed;
    @FXML private TextField totalPissed;
    @FXML private TextField normalCheckIn;
    @FXML private TextField limitedCheckIn;
    @FXML private TextField specialCheckIn;
    @FXML private TextField superCheckIn;
    @FXML private TextField totalCheckIn;
    @FXML private TextField normalVoteTime;
    @FXML private TextField limitedVoteTime;
    @FXML private TextField specialVoteTime;
    @FXML private TextField superVoteTime;
    @FXML private TextField totalVoteTime;
    @FXML private TextField normalComplete;
    @FXML private TextField limitedComplete;
    @FXML private TextField specialComplete;
    @FXML private TextField superComplete;
    @FXML private TextField totalComplete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStatistics();
    }

    private void getStatistics() {
        normalTotal.setText(Statistics.getStatistic("normalTotal") + "");
        limitedTotal.setText(Statistics.getStatistic("limitedTotal") + "");
        specialTotal.setText(Statistics.getStatistic("specialTotal") + "");
        superTotal.setText(Statistics.getStatistic("superTotal") + "");
        totalTotal.setText(Statistics.getStatistic("totalTotal") + "");
        normalVoted.setText(Statistics.getStatistic("normalVoted") + "");
        limitedVoted.setText(Statistics.getStatistic("limitedVoted") + "");
        specialVoted.setText(Statistics.getStatistic("specialVoted") + "");
        superVoted.setText(Statistics.getStatistic("superVoted") + "");
        totalVoted.setText(Statistics.getStatistic("totalVoted") + "");
        normalPissed.setText(Statistics.getStatistic("normalPissed") + "");
        limitedPissed.setText(Statistics.getStatistic("limitedPissed") + "");
        specialPissed.setText(Statistics.getStatistic("specialPissed") + "");
        superPissed.setText(Statistics.getStatistic("superPissed") + "");
        totalPissed.setText(Statistics.getStatistic("totalPissed") + "");
        normalCheckIn.setText(Statistics.getStatistic("normalCheckIn") + "");
        limitedCheckIn.setText(Statistics.getStatistic("limitedCheckIn") + "");
        specialCheckIn.setText(Statistics.getStatistic("specialCheckIn") + "");
        superCheckIn.setText(Statistics.getStatistic("superCheckIn") + "");
        totalCheckIn.setText(Statistics.getStatistic("totalCheckIn") + "");
        normalVoteTime.setText(Statistics.getStatistic("avgNormalVoteTime") + "");
        limitedVoteTime.setText(Statistics.getStatistic("avgLimitedVoteTime") + "");
        specialVoteTime.setText(Statistics.getStatistic("avgSpecialVoteTime") + "");
        superVoteTime.setText(Statistics.getStatistic("avgSuperVoteTime") + "");
        totalVoteTime.setText(Statistics.getStatistic("avgTotalVoteTime") + "");
        normalComplete.setText(Statistics.getStatistic("normalComplete") + "");
        limitedComplete.setText(Statistics.getStatistic("limitedComplete") + "");
        specialComplete.setText(Statistics.getStatistic("specialComplete") + "");
        superComplete.setText(Statistics.getStatistic("superComplete") + "");
        totalComplete.setText(Statistics.getStatistic("totalComplete") + "");
    }

}
