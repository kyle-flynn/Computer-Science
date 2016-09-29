package game1024.gui;

import game1024.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

/**
 * Created by kylef_000 on 9/28/2016.
 */
public class GamePanel extends JPanel {

    private KeyboardListener keyListener;
    private ButtonListener btnListener;
    private MenuListener menuListener;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem gameReset;
    private JMenuItem gameResize;
    private JMenuItem gameWinChange;

    private JButton slideUp;
    private JButton slideDown;
    private JButton slideLeft;
    private JButton slideRight;
    private JButton undo;
    private JPanel buttonPanel;
    private JPanel buttonContainer;
    private JPanel infoPanel;
    private JLabel score;

    private NumberTile[][] tiles;
    private int size;
    private int xOffset;
    private int yOffset;

    private int width;
    private int height;

    private NumberGame game;

    public GamePanel(int width, int height, NumberGame game) {
        this.width = width;
        this.height = height;
        this.game = game;
        this.size = 72;

        setLayout(null);
        resizeBoard(width, height);

        keyListener = new KeyboardListener();
        btnListener = new ButtonListener();
        menuListener = new MenuListener();
        buttonPanel = new JPanel();
        buttonContainer = new JPanel();
        infoPanel = new JPanel();
        score = new JLabel();

        slideUp = new JButton("Slide Up");
        slideDown = new JButton("Slide Down");
        slideLeft = new JButton("Slide Left");
        slideRight = new JButton("Slide Right");
        undo = new JButton("Undo");

        slideLeft.addActionListener(btnListener);
        slideUp.addActionListener(btnListener);
        slideDown.addActionListener(btnListener);
        slideRight.addActionListener(btnListener);
        undo.addActionListener(btnListener);

        addKeyListener(keyListener);

        setLayout(new BorderLayout());
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(slideLeft, BorderLayout.WEST);
        buttonPanel.add(slideUp, BorderLayout.NORTH);
        buttonPanel.add(slideDown, BorderLayout.SOUTH);
        buttonPanel.add(slideRight, BorderLayout.EAST);
        buttonPanel.add(undo, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.BLACK);
        buttonContainer.setBackground(Color.BLACK);

        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameReset = new JMenuItem("Reset Game");
        gameResize = new JMenuItem("Resize Board");
        gameWinChange = new JMenuItem("Change Winning Tile");

        gameReset.addActionListener(menuListener);
        gameResize.addActionListener(menuListener);
        gameWinChange.addActionListener(menuListener);

        menuBar.add(gameMenu);

        gameMenu.add(gameReset);
        gameMenu.add(gameResize);
        gameMenu.add(gameWinChange);

        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(score, BorderLayout.CENTER);

        score.setForeground(Color.WHITE);
        score.setFont(new Font("Helvetica", Font.PLAIN, 32));

        buttonContainer.add(score, BorderLayout.NORTH);
        buttonContainer.add(buttonPanel, BorderLayout.SOUTH);
        add(menuBar, BorderLayout.PAGE_START);
        add(infoPanel, BorderLayout.NORTH);
        add(buttonContainer, BorderLayout.SOUTH);

        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        renderBoard();
    }

    private void resizeBoard(int width, int height) {

        if (tiles != null) {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    remove(tiles[i][j]);
                }
            }
        }

        this.height = height;
        this.width = width;

        xOffset = (800/2) - ((size * width)/2);
        yOffset = (600/2) - ((size * height)/2) - 20;
        tiles = new NumberTile[height][width];

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

        setBackground(Color.BLACK);
        revalidate();
    }

    private void renderBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j].setText("");
            }
        }

        for (Cell cell : game.getNonEmptyTiles()) {
            if (tiles[cell.row][cell.column].getText().contains(cell.value + "") == false) {
                tiles[cell.row][cell.column].setText(cell.value + "");
            }
        }

        score.setText("Current Score: " + game.getScore());

        checkStatus();

    }

    private void checkStatus() {
        if (game.getStatus() == GameStatus.USER_LOST) {
            int option = JOptionPane.showConfirmDialog(this, "You have lost! Play Again?", "1024", JOptionPane.YES_NO_OPTION);

            if (option == 1) {
                System.exit(0);
            } else {
                game.reset();
                renderBoard();
            }

        } else if (game.getStatus() == GameStatus.USER_WON) {
            int option = JOptionPane.showConfirmDialog(this, "You have won! Congratulations! Play Again?", "1024", JOptionPane.YES_NO_OPTION);

            if (option == 1) {
                System.exit(0);
            } else {
                game.reset();
                renderBoard();
            }
        }
    }

    private class ButtonListener implements ActionListener {

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

    private class KeyboardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

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

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class MenuListener implements ActionListener {

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
//                    resizeBoard(width, height);
                    renderBoard();
                } catch (NumberFormatException nfe){
                    JOptionPane.showConfirmDialog(null, "Error making new board. Did you enter an integer?", "1024", JOptionPane.OK_OPTION);
                }
            } else if (e.getSource() == gameWinChange) {
                try {
                    int newVal = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new winning value (must be an integer)", "1024", JOptionPane.INFORMATION_MESSAGE));

                    // TODO - Not working...
//                    resizeBoard(width, height);
                    renderBoard();
                } catch (NumberFormatException nfe){
                    JOptionPane.showConfirmDialog(null, "Error making new board. Did you enter an integer?", "1024", JOptionPane.OK_OPTION);
                }
            }
        }
    }

}
