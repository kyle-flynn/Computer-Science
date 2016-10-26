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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStatistics();
    }

    private void getStatistics() {
        normalTotal.setText(Statistics.getStatistic("normalTotal") + "");
        limitedTotal.setText(Statistics.getStatistic("limitedTotal") + "");
        specialTotal.setText(Statistics.getStatistic("specialTotal") + "");
        totalTotal.setText(Statistics.getStatistic("totalTotal") + "");
    }

}
