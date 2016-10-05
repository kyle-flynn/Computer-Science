package game1024.gui;

import game1024.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*****************************************************************
Game Panel.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class GamePanel extends JPanel {

    /** Instance of keyboard listener (testing/efficiency purposes) */
    private KeyboardListener keyListener;

    /** Instance of button listener */
    private ButtonListener btnListener;

    /** Instance of TimerListener */
    private TimerListener timerListener;

    /** Button that slide the tiles up */
    private JButton slideUp;

    /** Button that slide the tiles down */
    private JButton slideDown;

    /** Button that slide the tiles left */
    private JButton slideLeft;

    /** Button that slide the tiles right */
    private JButton slideRight;

    /** Button that will undo the last move */
    private JButton undo;

    /** JPanel that contains all of the buttons */
    private JPanel buttonPanel;

    /** JPanel that will hold the button container */
    private JPanel buttonContainer;

    /** JPanel that holds info on the right side of the window */
    private JPanel rightInfoPanel;

    /** JPanel that holds info on the left side of the window */
    private JPanel leftInfoPanel;

    /** JLabel that holds the current game score */
    private JLabel score;

    /** JLabel that holds the game's all-time high score */
    private JLabel highScore;

    /** JLabel that only displays 'Current Score:' */
    private JLabel scoreText;

    /** JLabel that only displays 'High Score:' */
    private JLabel highscoreText;

    /** JLabel that only displays loses for session */
    private JLabel losesText;

    /** JLabel that only displays wins for session */
    private JLabel winsText;

    /** JLabel that displays the time left to win */
    private JLabel timerLabel;

    /** Timer object that counts down every second */
    private Timer timer;

    /** 2d array that holds the graphic game tiles */
    private NumberTile[][] tiles;

    /** Integer that holds the size of each tile in pixels */
    private int size;

    /** Integer that centers the board tile x-axis */
    private int xOffset;

    /** Integer that centers the board tile y-axis */
    private int yOffset;

    /** Holds the width of the game board */
    private int width;

    /** Holds the height of the game board */
    private int height;

    /** Controls wins in current session */
    private int wins;

    /** Controls loses in current session */
    private int loses;

    /** Holds amount of time left */
    private int timeLeft;

    /** Instance of the game */
    private NumberGame game;

    public GamePanel(int width, int height, NumberGame game) {
        this.width = width;
        this.height = height;
        this.game = game;
        this.size = 64;
        this.timeLeft = 300;
        this.wins = 0;
        this.loses = 0;

        /** Use a null layout to position our elements manually */
        setLayout(null);
        resizeBoard(width, height);

        /** Initializing all of our variables */
        keyListener = new KeyboardListener();
        btnListener = new ButtonListener();
        timerListener = new TimerListener();
        buttonPanel = new JPanel();
        buttonContainer = new JPanel();
        rightInfoPanel = new JPanel();
        leftInfoPanel = new JPanel();
        score = new JLabel();
        highScore = new JLabel();
        scoreText = new JLabel();
        highscoreText = new JLabel();
        losesText = new JLabel();
        winsText = new JLabel();
        timerLabel = new JLabel();

        /** Initializing our timer that runs every second */
        timer = new Timer(1000, timerListener);

        /** Initializing our buttons */
        slideUp = new JButton("Slide Up");
        slideDown = new JButton("Slide Down");
        slideLeft = new JButton("Slide Left");
        slideRight = new JButton("Slide Right");
        undo = new JButton("Undo");

        /** Adding button listeners to our buttons */
        slideLeft.addActionListener(btnListener);
        slideUp.addActionListener(btnListener);
        slideDown.addActionListener(btnListener);
        slideRight.addActionListener(btnListener);
        undo.addActionListener(btnListener);

        /** Adding key listener to our window */
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

        /** Dealing with layouts and adding scores to the panels */
        rightInfoPanel.setLayout(new BorderLayout());
        rightInfoPanel.add(score, BorderLayout.CENTER);
        rightInfoPanel.add(scoreText, BorderLayout.NORTH);
        rightInfoPanel.add(losesText, BorderLayout.SOUTH);
        rightInfoPanel.setBackground(Color.BLACK);
        leftInfoPanel.setLayout(new BorderLayout());
        leftInfoPanel.add(highScore, BorderLayout.CENTER);
        leftInfoPanel.add(highscoreText, BorderLayout.NORTH);
        leftInfoPanel.add(winsText, BorderLayout.SOUTH);
        leftInfoPanel.setBackground(Color.BLACK);

        /** Setting font of the score and high score labels */
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Consolas", Font.PLAIN, 48));
        score.setVerticalAlignment(SwingConstants.CENTER);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        highScore.setForeground(Color.WHITE);
        highScore.setFont(new Font("Consolas", Font.PLAIN, 48));
        highScore.setHorizontalAlignment(SwingConstants.CENTER);
        highScore.setVerticalAlignment(SwingConstants.CENTER);
        scoreText.setForeground(Color.WHITE);
        scoreText.setFont(new Font("Consolas", Font.PLAIN, 24));
        scoreText.setText("Current Score");
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        scoreText.setVerticalAlignment(SwingConstants.BOTTOM);
        highscoreText.setForeground(Color.WHITE);
        highscoreText.setFont(new Font("Consolas", Font.PLAIN, 24));
        highscoreText.setText("High Score");
        highscoreText.setHorizontalAlignment(SwingConstants.CENTER);
        highscoreText.setVerticalAlignment(SwingConstants.BOTTOM);
        winsText.setForeground(Color.WHITE);
        winsText.setText("Wins: 0");
        winsText.setFont(new Font("Consolas", Font.PLAIN, 36));
        winsText.setHorizontalAlignment(SwingConstants.CENTER);
        winsText.setVerticalAlignment(SwingConstants.CENTER);
        losesText.setForeground(Color.WHITE);
        losesText.setText("Loses: 0");
        losesText.setFont(new Font("Consolas", Font.PLAIN, 36));
        losesText.setHorizontalAlignment(SwingConstants.CENTER);
        losesText.setVerticalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
        timerLabel.setText("Time Left to Win: " + timeLeft);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setVerticalAlignment(SwingConstants.CENTER);

        /** Adding our containers to 'this' panel */
        buttonContainer.add(buttonPanel, BorderLayout.SOUTH);
        add(rightInfoPanel, BorderLayout.EAST);
        add(leftInfoPanel, BorderLayout.WEST);
        add(buttonContainer, BorderLayout.SOUTH);
        add(timerLabel, BorderLayout.NORTH);

        /** Parameters for 'this' panel */
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        renderBoard();

        /** Start the countdown to win */
        timer.start();
    }

    /*****************************************************************
    Method that is responsible for resize and repainting the board.
    @param width width of the board.
    @param height height of the board.
    *****************************************************************/
    private void resizeBoard(int width, int height) {
        this.height = height;
        this.width = width;

        /** In order to make sure our board is truly dynamic,
        we must check to see if the board width/height
        exceeds a magic number (414 in this case - tested)
        and if it does, keep decreasing the tile size
        until it fits inside the board. */
        int boardWidth = (size*width) + (width*5);
        int boardHeight = (size*height) + (height*5);

        while (boardWidth > 414 || boardHeight > 414) {
            size--;
            boardWidth = (size*width) + (width*5);
            boardHeight = (size*height) + (height*5);
        }

        /** Calculating our centering variables. We do this by
        dividing the screen width/height by 2, then using the
        size of each tile and width/height of the board.
        The yOffset has another offset of 20. */
        xOffset = (800/2) - ((size*width)/2);
        yOffset = (600/2) - ((size*height)/2) - 80;
        tiles = new NumberTile[height][width];

        /** Game logic required */
        game.resizeBoard(height, width, 1024);
        game.placeRandomValue();
        game.placeRandomValue();


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** Setting each tile to a NumberTile */
                tiles[i][j] = new NumberTile("", JLabel.CENTER);
                tiles[i][j].setFont(new Font("Helvetica", Font.PLAIN, 32));

                /** Setting the position of our rectangle using
                x/y offset calculations, as well with some
                spacing between tiles for neatness. */
                tiles[i][j].setBounds(
                        new Rectangle(
                                (xOffset - ((j*5)/2)) + (size*j) + (j*5),
                                yOffset + (size*i) + (i*5), size, size));

                add(tiles[i][j]);
            }
        }

        /** Necessary for the JPanel to show our tiles */
        setBackground(Color.BLACK);
        revalidate();
    }

    /*****************************************************************
    Method that renders the board to the screen and changes values.
    This needs to be public becasue the GameGUI class will need
    to render the board after a reset, or board resize.
    *****************************************************************/
    public void renderBoard() {
        /** By default, set label to empty */
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j].setText("");
            }
        }

        /** If a tile isn't empty, render it's value */
        for (Cell cell : game.getNonEmptyTiles()) {
            if (!tiles[cell.row][cell.column].getText().contains(
                    cell.value + "")) {
                tiles[cell.row][cell.column].setText(cell.value + "");
            }
        }

        /** Sets the text of our scores */
        score.setText("" + game.getScore());
        highScore.setText("" + game.getHighScore());

        /** Check to see the status of our game */
        checkStatus();

    }

    /*****************************************************************
    Method that checks if the user has lost or won, and prompts to
    play again.
    *****************************************************************/
    public void checkStatus() {
        if (game.getStatus() != GameStatus.IN_PROGRESS) {

            /** Create a variable to save space from duplicate code. */
            String message = "";

            if (game.getStatus() == GameStatus.USER_WON) {
                message = "You have won! Congratulations! Play Again?";
                wins++;
            }
            if (game.getStatus() == GameStatus.USER_LOST) {
                message = "You have lost! Play Again?";
                loses++;
            }

            winsText.setText("Wins: " + wins);
            losesText.setText("Loses: " + loses);

            int option = JOptionPane.showConfirmDialog(
                    this, message,
                    "1024", JOptionPane.YES_NO_OPTION);

            /** Exit if 'no' is pressed, continue if 'Yes' is pressed. */
            if (option == 1) {
                game.saveHighScore();
                System.exit(0);
            } else {
                reset();
            }
        }
    }

    /*****************************************************************
    Method that simply resets the current game, renders the board,
    and resets the time.
    *****************************************************************/
    public void reset() {
        game.reset();
        game.setWinningValue(1024);
        timeLeft = 300;
        renderBoard();
    }

    /*****************************************************************
    private ButtonListener class.
    @author Kyle Flynn
    @version v1.0
    *****************************************************************/
    private class ButtonListener implements ActionListener {

        /*****************************************************************
        Overriden method that controls when an action is invoked.
        We use it to listen for button clicks.
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
                    JOptionPane.showMessageDialog(
                            null, "Can't undo that far!",
                            "1024", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /*****************************************************************
    private KeyboardListener class.
    @author Kyle Flynn
    @version v1.0
    *****************************************************************/
    private class KeyboardListener implements KeyListener {

        /*****************************************************************
        Overriden method that controls when a key is typed. We don't
        use this.
        *****************************************************************/
        @Override
        public void keyTyped(KeyEvent e) {}

        /*****************************************************************
        Overriden method that controls when a key is pressed. We use
        this for easier gameplay.
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
                    JOptionPane.showMessageDialog(
                            null, "Can't undo that far!",
                            "1024", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        /*****************************************************************
        Overriden method that controls when a key is released. We
        don't need this.
        *****************************************************************/
        @Override
        public void keyReleased(KeyEvent e) {}
    }

    /*****************************************************************
    private KeyboardListener class.
    @author Kyle Flynn
    @version v1.0
    *****************************************************************/
    private class TimerListener implements ActionListener {

        /*****************************************************************
        Overriden method that controls when an action is invoked.
        We use it to listen for the change in time.
        *****************************************************************/
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timeLeft <= 0 &&
                    game.getStatus() != GameStatus.IN_PROGRESS) {
                timerLabel.setText("Time Left to Win: " + timeLeft);

                int option = JOptionPane.showConfirmDialog(
                        null, "You have lost! Play Again?",
                        "1024", JOptionPane.YES_NO_OPTION);

                /** Exit if 'no' is pressed, continue if 'Yes' is pressed. */
                if (option == 1) {
                    game.saveHighScore();
                    System.exit(0);
                } else {
                    reset();
                }
            } else {
                if (game.getStatus() == GameStatus.IN_PROGRESS) {
                    timerLabel.setText("Time Left to Win: " + timeLeft);
                    timeLeft--;
                }
            }
        }
    }

}
