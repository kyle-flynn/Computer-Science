package game1024.gui;

import game1024.NumberGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*****************************************************************
GameGUI Container class.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class GameGUI {

    /** Holds the width of the window */
    private int width;

    /** Holds the height of the window */
    private int height;

    /** NumberGame instance that we pass to the game panel. */
    private NumberGame game;

    /** JFrame object to create our window */
    private JFrame frame;

    /** Instance of the board panel that will hold our entire game. */
    private GamePanel boardPanel;

    /** Instance of menu listener */
    private MenuListener menuListener;

    /** Main JMenuBar that will hold all menu items */
    private JMenuBar menuBar;

    /** Game Menu that will allow for reset, etc. */
    private JMenu gameMenu;

    /** Menu Item that will reset game */
    private JMenuItem gameReset;

    /** Menu Item that will resize the board */
    private JMenuItem gameResize;

    /** Menu Item that will change the winning value */
    private JMenuItem gameWinChange;

    /*****************************************************************
    Constructor that initializes our variables.
    *****************************************************************/
    public GameGUI() {
        width = 800;
        height = 600;
        frame = new JFrame();

        /** Initializing our menu options */
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameReset = new JMenuItem("Reset Game");
        gameResize = new JMenuItem("Resize Board");
        gameWinChange = new JMenuItem("Change Winning Tile");

        menuListener = new MenuListener();
        game = new NumberGame();

        /** Initializing our board with a default 4x4 grid. */
        boardPanel = new GamePanel(4, 4, game);
    }

    /*****************************************************************
    Method that initializes the parameters of the JFrame and creates
    our window.
    *****************************************************************/
    public void init() {

        /** Adding menu listeners to the menu items */
        gameReset.addActionListener(menuListener);
        gameResize.addActionListener(menuListener);
        gameWinChange.addActionListener(menuListener);

        /** Connecting our menu items to the menu */
        menuBar.add(gameMenu);
        gameMenu.add(gameReset);
        gameMenu.add(gameResize);
        gameMenu.add(gameWinChange);

        /** Setting parameters for the JFrame window. */
        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
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

    /*****************************************************************
    private MenuListener class.
    @author Kyle Flynn
    @version v1.0
    *****************************************************************/
    private class MenuListener implements ActionListener {

        /*****************************************************************
        Overriden method that controls when an action is invoked.
        We use it to listen for when a menu item is clicked.
        *****************************************************************/
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == gameReset) {
                boardPanel.reset();
            } else if (e.getSource() == gameResize) {
                try {
                    int width = Integer.parseInt(JOptionPane.showInputDialog(
                            null, "Enter width of new board (must be an integer)",
                            "1024", JOptionPane.INFORMATION_MESSAGE));
                    int height = Integer.parseInt(JOptionPane.showInputDialog(
                            null, "Enter height of new board (must be an integer)",
                            "1024", JOptionPane.INFORMATION_MESSAGE));

                    /** In order for the resize board to work, we must
                    remove the panel from the JFrame, declare a new
                    instance, and revalidate/repaint the frame. */
                    frame.remove(boardPanel);
                    boardPanel = new GamePanel(width, height, game);
                    frame.add(boardPanel);
                    frame.revalidate();
                    frame.repaint();
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error making new board. Did you enter an integer?",
                            "1024",
                            JOptionPane.OK_OPTION);
                }
            } else if (e.getSource() == gameWinChange) {
                try {
                    int newVal = Integer.parseInt(JOptionPane.showInputDialog(
                            null, "Enter new winning value (must be an integer)",
                            "1024", JOptionPane.INFORMATION_MESSAGE));

                    game.setWinningValue(newVal);
                    boardPanel.checkStatus();
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error making new board. Did you enter an integer?",
                            "1024",
                            JOptionPane.OK_OPTION);
                }
            }
        }

    }
}