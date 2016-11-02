package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import votingbooth.Booth;
import votingbooth.CheckInTable;
import votingbooth.Statistics;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Kyle Flynn on 11/2/2016.
 */
public class ComplexOutputController implements Initializable {

    @FXML private AnchorPane window;

    private LinkedList<Booth> booths;
    private LinkedList<CheckInTable> tables;
    private int leftInLine;

    private Label[] boothFields;
    private Label[] tableFields;
    private Label boothQ;

    private int boothID = 0;
    private int tableID = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            booths = (LinkedList<Booth>) Statistics.getStatistic("booths");
            tables = (LinkedList<CheckInTable>) Statistics.getStatistic("tables");
            leftInLine = Integer.parseInt(Statistics.getStatistic("leftInLine") + "");

            boothFields = new Label[booths.size()];
            tableFields = new Label[tables.size()];

            displayData();
        } catch(Exception e) {
            // Show error dialog!
            e.printStackTrace();
        }
    }

    private void displayData() {

        for (CheckInTable table : tables) {
            String voters = "";
            for (int i = 0; i < table.getVoterQ(); i++) {
                voters += "*";
            }

            tableFields[tableID] = new Label();
            tableFields[tableID].setText(voters + " (" + table.getVoterQ() +
                    ") left in queue for Table " + tableID +
                    " | Max Q Length: " + table.getMaxQlength() +
                    " | Voters Checked In: " + table.getCheckedIn());

            tableFields[tableID].setLayoutX(20);
            tableFields[tableID].setLayoutY(tableID * 20);

            window.getChildren().add(tableFields[tableID]);

            tableID++;
        }

        for (Booth b : booths) {

            String voters = "";

            if (b.inUse()) {
                voters = "* Booth " + boothID + " still in use | ";
            }

            boothFields[boothID] = new Label();
            boothFields[boothID].setText(voters + "Voters that voted in booth " + boothID + ": " + b.getThroughPut());

            boothFields[boothID].setLayoutX(20);
            boothFields[boothID].setLayoutY((tableID * 20) + (boothID * 20));

            window.getChildren().add(boothFields[boothID]);

            boothID++;
        }

        String voters = "";
        for (int i = 0; i < leftInLine; i++) {
            voters += "*";
        }

        boothQ = new Label();
        boothQ.setText(voters + " voters left in line (" + leftInLine + ").");

        boothQ.setLayoutX(30);
        boothQ.setLayoutY((tableID * 20) + (boothID * 20));

        boothQ.setPrefWidth(window.getPrefWidth() - 20);
        boothQ.setWrapText(true);

        window.getChildren().add(boothQ);
    }

}
