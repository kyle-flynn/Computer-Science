package game1024.gui;

import game1024.NumberGame;

import javax.swing.*;
import java.awt.*;

/*****************************************************************
 * GameGUI Container class.
 * @author Kyle Flynn
 * @version 1.0
 *****************************************************************/
public class GameGUI {

    /** Holds the width of the window **/
    private int width;

    /** Holds the height of the window **/
    private int height;

    /** NumberGame instance that we pass to the game panel. **/
    private NumberGame game;

    /** JFrame object to create our window **/
    private JFrame frame;

    /** Instance of the board panel that will hold our entire game. **/
    private GamePanel boardPanel;

    /*****************************************************************
     * Constructor that initializes our variables.
     *****************************************************************/
    public GameGUI() {
        width = 800;
        height = 600;
        frame = new JFrame();
        game = new NumberGame();
        boardPanel = new GamePanel(4, 4, game);
    }

    /*****************************************************************
     * Method that initializes the parameters of the JFrame and creates
     * our window.
     *****************************************************************/
    public void init() {

        /** Setting parameters for the JFrame window. **/
        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setTitle("1024 Revamped");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GameGUI graphicalUI = new GameGUI();
        graphicalUI.init();
    }

}
