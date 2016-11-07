package votingbooth.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*****************************************************************
Basic Simulator launcher class. Responsible for launching the 'C'
and 'B' parts of the assignment. This is done using JavaFX and FXML.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class BasicSimulator extends Application {

    /*****************************************************************
    Overriden method that is called by JavaFX when an application is
    started.
    @throws Exception Thrown whenever there is an error loading the FXML
    file, or generating a new scene.
    @param primaryStage The stage that the program starts with. JavaFX
    handles this part.
    *****************************************************************/
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            /* This is the main 'parent' element of the entire
            application. */
            Parent root = FXMLLoader.load(
                    getClass().getResource("/BasicVotingSimulator.fxml"));

            /* This is responsible for containing the current scene of
            the application. */
            Scene scene = new Scene(root);

            /* Setting properties of the stage. */
            primaryStage.setScene(scene);
            primaryStage.setTitle("Basic Voting Simulator v1.0");
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*****************************************************************
    Main method used by every single Java program. Uses the static
    launch method to start the JavaFX program.
    @param args The program's launch arguments.
    *****************************************************************/
    public static void main(String[] args) {
        launch(args);
    }

}
