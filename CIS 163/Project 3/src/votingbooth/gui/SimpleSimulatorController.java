package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import votingbooth.Statistics;

import java.net.URL;
import java.util.ResourceBundle;

/*****************************************************************
Simple Simulator Controller class. Responsible for linking the
front-end code to the back-end, and run the simulation. This
fulfills the requirements for part 'B'.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class SimpleSimulatorController implements Initializable {

    /** TextField that displays the total normal voters. */
    @FXML private TextField normalTotal;

    /** TextField that displays the total limited time voters. */
    @FXML private TextField limitedTotal;

    /** TextField that displays the total special needs voters. */
    @FXML private TextField specialTotal;

    /** TextField that displays the total super special needs voters. */
    @FXML private TextField superTotal;

    /** TextField that displays the total voters. */
    @FXML private TextField totalTotal;

    /** TextField that displays normal voters that voted. */
    @FXML private TextField normalVoted;

    /** TextField that displays limited time voters that voted. */
    @FXML private TextField limitedVoted;

    /** TextField that displays special needs voters that voted. */
    @FXML private TextField specialVoted;

    /** TextField that displays super special needs voters that voted. */
    @FXML private TextField superVoted;

    /** TextField that displays total voters that voted. */
    @FXML private TextField totalVoted;

    /** TextField that displays normal voters who left. */
    @FXML private TextField normalPissed;

    /** TextField that displays limited time voters who left. */
    @FXML private TextField limitedPissed;

    /** TextField that displays special needs voters who left. */
    @FXML private TextField specialPissed;

    /** TextField that displays super special needs voters who left. */
    @FXML private TextField superPissed;

    /** TextField that displays total voters who left. */
    @FXML private TextField totalPissed;

    /** TextField that displays check in time for normal voters. */
    @FXML private TextField normalCheckIn;

    /** TextField that displays check in time for limited voters. */
    @FXML private TextField limitedCheckIn;

    /** TextField that displays check in time for special voters. */
    @FXML private TextField specialCheckIn;

    /** TextField that displays check in time for super special voters. */
    @FXML private TextField superCheckIn;

    /** TextField that displays check in time for all voters. */
    @FXML private TextField totalCheckIn;

    /** TextField that displays vote time for normal voters. */
    @FXML private TextField normalVoteTime;

    /** TextField that displays vote time for limited voters. */
    @FXML private TextField limitedVoteTime;

    /** TextField that displays vote time for special voters. */
    @FXML private TextField specialVoteTime;

    /** TextField that displays vote time for super special voters. */
    @FXML private TextField superVoteTime;

    /** TextField that displays vote time for all voters. */
    @FXML private TextField totalVoteTime;

    /** TextField that displays normal voters who finished. */
    @FXML private TextField normalComplete;

    /** TextField that displays limited voters who finished. */
    @FXML private TextField limitedComplete;

    /** TextField that displays special needs voters who finished. */
    @FXML private TextField specialComplete;

    /** TextField that displays super special needs voters who finished. */
    @FXML private TextField superComplete;

    /** TextField that displays all voters who finished. */
    @FXML private TextField totalComplete;

    /*****************************************************************
    Overriden method that occurs when the application has loaded, and
    is ready to run code.
    @param location The location of the application. Handled by JavaFX.
    @param resources The ResourceBundle of the application. Handled by
    JavaFX.
    *****************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStatistics();
    }

    /*****************************************************************
    Private helper method that gathers all of the statistics from our
    statistic class. We also set the label text to the value of the
    statistic.
    *****************************************************************/
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
