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

/*****************************************************************
Complex Simulator Output class. Responsible for displaying the
second part of the 'A' requirement of the assignment.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class ComplexOutputController implements Initializable {

    /** Contains the application AnchorPane that we will be adding to */
    @FXML private AnchorPane window;

    /** LinkedList of all booths. We use LinkedList because we are not
    modifying data, but instead just reading and adding/subtracting. */
    private LinkedList<Booth> booths;

    /** LinkedList of all tables. We use LinkedList because we are not
    modifying data, but instead just reading and adding/subtracting. */
    private LinkedList<CheckInTable> tables;

    /** Contains the amount of people left in the booth line */
    private int leftInLine;

    /** Array of Labels that display booth statistics */
    private Label[] boothFields;

    /** Array of Labels that display table statistics */
    private Label[] tableFields;

    /** Single label that will display booth queue statistics */
    private Label boothQ;

    /** Contains the index of booths */
    private int boothID;

    /** Contains the index of tables */
    private int tableID;

    /*****************************************************************
    Overriden method that occurs when the application has loaded, and
    is ready to run code.
    @param location The location of the application. Handled by JavaFX.
    @param resources The ResourceBundle of the application. Handled by
    JavaFX.
    *****************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            /* Initializing all of our declared variables. We grab them
            from our Statistics class, and cast them to a LinkedList
            because we are not modifying their data, but instead just
            reading it. */
            booths = (LinkedList<Booth>) Statistics.getStatistic("booths");
            tables = (LinkedList<CheckInTable>) Statistics.getStatistic("tables");
            leftInLine = Integer.parseInt(Statistics.getStatistic("leftInLine") + "");

            boothFields = new Label[booths.size()];
            tableFields = new Label[tables.size()];

            boothID = 0;
            tableID = 0;

            /* If there are no problems getting the data, display it. */
            displayData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
    Private helper method that displays all of the data with "*'s".
    This is the second part to the 'A' requirement.
    *****************************************************************/
    private void displayData() {


        for (CheckInTable table : tables) {

            /* Will display voters with *'s */
            String voters = "";
            for (int i = 0; i < table.getVoterQ(); i++) {

                /* Appending voters to the string */
                voters += "*";
            }

            /* Initializing labels from the array at the current index. */
            tableFields[tableID] = new Label();
            tableFields[tableID].setText(voters + " (" + table.getVoterQ()
                    + ") left in queue for Table " + tableID +
                    " | Max Q Length: " + table.getMaxQlength() +
                    " | Voters Checked In: " + table.getCheckedIn());

            /* Setting location of the label. We use some math to
            determine the y-axis value. */
            tableFields[tableID].setLayoutX(20);
            tableFields[tableID].setLayoutY(tableID * 20);

            /* Adding the node to the anchor pane. */
            window.getChildren().add(tableFields[tableID]);

            tableID++;
        }

        for (Booth b : booths) {

            /* Will display voter with a * */
            String voters = "";
            if (b.inUse()) {

                /* Appending the voter to the string */
                voters = "* Booth " + boothID + " still in use | ";
            }

            /* Initializing labels from the array at the current index. */
            boothFields[boothID] = new Label();
            boothFields[boothID].setText(voters + "Voters that voted in booth "
                    + boothID + ": " + b.getThroughPut());

            /* Setting location of the label. We use some math to
            determine the y-axis value. */
            boothFields[boothID].setLayoutX(20);
            boothFields[boothID].setLayoutY((tableID * 20) + (boothID * 20));

            /* Adding the node to the anchor pane. */
            window.getChildren().add(boothFields[boothID]);

            boothID++;
        }

        /* Will display voters in the booth line. */
        String voters = "";
        for (int i = 0; i < leftInLine; i++) {

            /* Appending the string with the voters. */
            voters += "*";
        }

        /* Initializing the boothQ label. */
        boothQ = new Label();
        boothQ.setText(voters + " voters left in line " +
                "(" + leftInLine + ").");

        /* Setting the location of the label based on current values. */
        boothQ.setLayoutX(30);
        boothQ.setLayoutY((tableID * 20) + (boothID * 20));

        /* Setting properties of label so it does not place
        text outside of the window space.*/
        boothQ.setPrefWidth(window.getPrefWidth() - 20);
        boothQ.setWrapText(true);

        /* Adding the node to the anchor pane. */
        window.getChildren().add(boothQ);
    }

}
