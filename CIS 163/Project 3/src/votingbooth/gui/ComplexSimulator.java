package votingbooth.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by kylef_000 on 10/31/2016.
 */
public class ComplexSimulator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ComplexVotingSimulator.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Complex Voting Simulator v100000000.013");
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // IDEAS:
        /*
        * Main GUI is very simple, and only takes in booths, tables, etc.
        * It shows two buttons: Show Output, and Show Statistics
        * Show output will show people in lines
        * Show statistics will display shit loads of statistics
        *  -For statistics, show the highest and lowest times
        */
        launch(args);
    }

}
