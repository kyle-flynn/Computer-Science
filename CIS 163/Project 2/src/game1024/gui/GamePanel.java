package game1024.gui;

import game1024.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*****************************************************************
 * Game Panel.
 * @author Kyle Flynn
 * @version 1.0
 *****************************************************************/
public class GamePanel extends JPanel {

    /** Instance of keyboard listener (testing/efficiency purposes) **/
    private KeyboardListener keyListener;

    /** Instance of button listener **/
    private ButtonListener btnListener;

    /** Instance of menu listener **/
    private MenuListener menuListener;

    /** Main JMenuBar that will hold all menu items **/
    private JMenuBar menuBar;

    /** Game Menu that will allow for reset, etc. **/
    private JMenu gameMenu;

    /** Menu Item that will reset game **/
    private JMenuItem gameReset;

    /** Menu Item that will resize the board **/
    private JMenuItem gameResize;

    /** Menu Item that will change the winning value **/
    private JMenuItem gameWinChange;

    /** Button that slide the tiles up **/
    private JButton slideUp;

    /** Button that slide the tiles down **/
    private JButton slideDown;

    /** Button that slide the tiles left **/
    private JButton slideLeft;

    /** Button that slide the tiles right **/
    private JButton slideRight;

    /** Button that will undo the last move **/
    private JButton undo;

    /** JPanel that contains all of the buttons **/
    private JPanel buttonPanel;

    /** JPanel that will hold the button container **/
    private JPanel buttonContainer;

    /** JPanel that holds info on the right side of the window **/
    private JPanel rightInfoPanel;

    /** JPanel that holds info on the left side of the window **/
    private JPanel leftInfoPanel;

    /** JLabel that holds the current game score **/
    private JLabel score;

    /** JLabel that holds the game's all-time high score **/
    private JLabel highScore;

    /** 2d array that holds the graphic game tiles **/
    private NumberTile[][] tiles;

    /** Integer that holds the size of each tile in pixels **/
    private int size;

    /** Integer that centers the board tile x-axis **/
    private int xOffset;

    /** Integer that centers the board tile y-axis **/
    private int yOffset;

    /** Holds the width of the game board **/
    private int width;

    /** Holds the height of the game board **/
    private int height;

    /** Instance of the game **/
    private NumberGame game;

    public GamePanel(int width, int height, NumberGame game) {
        this.width = width;
        this.height = height;
        this.game = game;
        this.size = 72;

        /** Use a null layout to position our elements manually **/
        setLayout(null);
        resizeBoard(width, height);

        /** Initializing all of our variables **/
        keyListener = new KeyboardListener();
        btnListener = new ButtonListener();
        menuListener = new MenuListener();
        buttonPanel = new JPanel();
        buttonContainer = new JPanel();
        rightInfoPanel = new JPanel();
        leftInfoPanel = new JPanel();
        score = new JLabel();
        highScore = new JLabel();

        /** Initializing our buttons **/
        slideUp = new JButton("Slide Up");
        slideDown = new JButton("Slide Down");
        slideLeft = new JButton("Slide Left");
        slideRight = new JButton("Slide Right");
        undo = new JButton("Undo");

        /** Adding button listeners to our buttons **/
        slideLeft.addActionListener(btnListener);
        slideUp.addActionListener(btnListener);
        slideDown.addActionListener(btnListener);
        slideRight.addActionListener(btnListener);
        undo.addActionListener(btnListener);

        /** Adding key listener to our window **/
        addKeyListener(keyListener);

        /** Adding buttons to the button panel */
        setLayout(new BorderLayout());
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(slideLeft, BorderLayout.WEST);
        buttonPanel.add(slideUp, BorderLayout.NORTH);
        buttonPanel.add(slideDown, BorderLayout.SOUTH);
        buttonPanel.add(slideRight, BorderLayout.EAST);
        buttonPanel.add(undo, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.BLACK);
        buttonContainer.setBackground(Color.BLACK);

        /** Initializing our menu options **/
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameReset = new JMenuItem("Reset Game");
        gameResize = new JMenuItem("Resize Board");
        gameWinChange = new JMenuItem("Change Winning Tile");

        /** Adding menu listeners to the menu items **/
        gameReset.addActionListener(menuListener);
        gameResize.addActionListener(menuListener);
        gameWinChange.addActionListener(menuListener);

        /** Connecting our menu items to the menu **/
        menuBar.add(gameMenu);
        gameMenu.add(gameReset);
        gameMenu.add(gameResize);
        gameMenu.add(gameWinChange);

        /** Dealing with layouts and adding scores to the panels **/
        rightInfoPanel.setLayout(new BorderLayout());
        rightInfoPanel.add(score, BorderLayout.CENTER);
        rightInfoPanel.setBackground(Color.BLACK);
        leftInfoPanel.setLayout(new BorderLayout());
        leftInfoPanel.add(highScore, BorderLayout.CENTER);
        leftInfoPanel.setBackground(Color.BLACK);

        /** Setting font of the score and high score labels **/
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Helvetica", Font.PLAIN, 24));
        highScore.setForeground(Color.WHITE);
        highScore.setFont(new Font("Helvetica", Font.PLAIN, 24));

        /** Adding our containers to 'this' panel **/
        buttonContainer.add(buttonPanel, BorderLayout.SOUTH);
        add(menuBar, BorderLayout.PAGE_START);
        add(rightInfoPanel, BorderLayout.WEST);
        add(leftInfoPanel, BorderLayout.EAST);
        add(buttonContainer, BorderLayout.SOUTH);

        /** Parameters for 'this' panel **/
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        renderBoard();
    }

    /*****************************************************************
     * Method that is responsible for resize and repainting the board.
     * @param width width of the board.
     * @param height height of the board.
     *****************************************************************/
    private void resizeBoard(int width, int height) {

        /** TODO - WHY DOESN'T THIS WORK **/
        if (tiles != null) {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    remove(tiles[i][j]);
                }
            }
        }

        this.height = height;
        this.width = width;

        /** Calculating our centering variables. We do this by
         * dividing the screen width/height by 2, then using the
         * size of each tile and width/height of the board.
         * The yOffset has another offset of 20. **/
        xOffset = (800/2) - ((size * width)/2);
        yOffset = (600/2) - ((size * height)/2) - 20;
        tiles = new NumberTile[height][width];

        /** Game logic required **/
        game.resizeBoard(width, height, 1024);
        game.placeRandomValue();
        game.placeRandomValue();


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new NumberTile("", JLabel.CENTER);
                tiles[i][j].setFont(new Font("Helvetica", Font.PLAIN, 32));
                tiles[i][j].setBounds(new Rectangle((xOffset - ((j*5)/2)) + (size*j) + (j*5), yOffset + (size*i) + (i*5), size, size));

                add(tiles[i][j]);
            }
        }

        /** Necessary for the JPanel to show our tiles **/
        setBackground(Color.BLACK);
        revalidate();
    }

    /*****************************************************************
     * Method that renders the board to the screen and changes values.
     *****************************************************************/
    private void renderBoard() {
        /** By default, set label to empty **/
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j].setText("");
            }
        }

        /** If a tile isn't empty, render it's value **/
        for (Cell cell : game.getNonEmptyTiles()) {
            if (tiles[cell.row][cell.column].getText().contains(cell.value + "") == false) {
                tiles[cell.row][cell.column].setText(cell.value + "");
            }
        }

        /** Sets the text of our scores **/
        score.setText("Current Score: " + game.getScore());
        highScore.setText("High Score: " + game.getHighScore());

        /** Check to see the status of our game **/
        checkStatus();

    }

    /*****************************************************************
     * Method that checks if the user has lost or won, and prompts to
     * play again.
     *****************************************************************/
    private void checkStatus() {
        if (game.getStatus() == GameStatus.USER_LOST) {
            int option = JOptionPane.showConfirmDialog(this, "You have lost! Play Again?", "1024", JOptionPane.YES_NO_OPTION);

            if (option == 1) {
                game.saveHighScore();
                System.exit(0);
            } else {
                game.reset();
                renderBoard();
            }

        } else if (game.getStatus() == GameStatus.USER_WON) {
            int option = JOptionPane.showConfirmDialog(this, "You have won! Congratulations! Play Again?", "1024", JOptionPane.YES_NO_OPTION);

            if (option == 1) {
                game.saveHighScore();
                System.exit(0);
            } else {
                game.reset();
                renderBoard();
            }
        }
    }

    /*****************************************************************
     * private ButtonListener class.
     * @author Kyle Flynn
     * @version v1.0
     *****************************************************************/
    private class ButtonListener implements ActionListener {

        /*****************************************************************
         * Overriden method that controls when an action is invoked.
         * We use it to listen for button clicks.
         *****************************************************************/
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == slideUp) {
                game.slide(SlideDirection.UP);
                renderBoard();
            } else if (e.getSource() == slideDown) {
                game.slide(SlideDirection.DOWN);
                renderBoard();
            } else if (e.getSource() == slideLeft) {
                game.slide(SlideDirection.LEFT);
                renderBoard();
            } else if (e.getSource() == slideRight) {
                game.slide(SlideDirection.RIGHT);
                renderBoard();
            } else if (e.getSource() == undo) {
                try {
                    game.undo();
                    renderBoard();
                } catch(IllegalStateException ise) {
                    JOptionPane.showMessageDialog(null, "Can't undo that far!", "1024", JOptionPane.OK_OPTION);
                }
            }
        }
    }

    /*****************************************************************
     * private KeyboardListener class.
     * @author Kyle Flynn
     * @version v1.0
     *****************************************************************/
    private class KeyboardListener implements KeyListener {

        /*****************************************************************
         * Overriden method that controls when a key is typed. We don't
         * use this.
         *****************************************************************/
        @Override
        public void keyTyped(KeyEvent e) {

        }

        /*****************************************************************
         * Overriden method that controls when a key is pressed. We use
         * this for easier gameplay.
         *****************************************************************/
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                game.slide(SlideDirection.UP);
                renderBoard();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                game.slide(SlideDirection.DOWN);
                renderBoard();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                game.slide(SlideDirection.LEFT);
                renderBoard();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                game.slide(SlideDirection.RIGHT);
                renderBoard();
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                try {
                    game.undo();
                    renderBoard();
                } catch(IllegalStateException ise) {
                    JOptionPane.showMessageDialog(null, "Can't undo that far!", "1024", JOptionPane.OK_OPTION);
                }
            }
        }

        /*****************************************************************
         * Overriden method that controls when a key is released. We
         * don't need this.
         *****************************************************************/
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /*****************************************************************
     * private MenuListener class.
     * @author Kyle Flynn
     * @version v1.0
     *****************************************************************/
    private class MenuListener implements ActionListener {

        /*****************************************************************
         * Overriden method that controls when an action is invoked.
         * We use it to listen for when a menu item is clicked.
         *****************************************************************/
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == gameReset) {
                game.reset();
                renderBoard();
            } else if (e.getSource() == gameResize) {
                try {
                    int width = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter width of new board (must be an integer)", "1024", JOptionPane.INFORMATION_MESSAGE));
                    int height = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter height of new board (must be an integer)", "1024", JOptionPane.INFORMATION_MESSAGE));

                    // TODO - Not working...
                    resizeBoard(width, height);
                    renderBoard();
                } catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Error making new board. Did you enter an integer?", "1024", JOptionPane.OK_OPTION);
                }
            } else if (e.getSource() == gameWinChange) {
                try {
                    int newVal = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new winning value (must be an integer)", "1024", JOptionPane.INFORMATION_MESSAGE));

                    // TODO - Not working...
//                    resizeBoard(width, height);
                    renderBoard();
                } catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Error making new board. Did you enter an integer?", "1024", JOptionPane.OK_OPTION);
                }
            }
        }
    }

}
