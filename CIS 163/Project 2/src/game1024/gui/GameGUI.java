package game1024.gui;

import game1024.NumberGame;
import game1024.NumberSlider;
import game1024.NumberTile;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by kylef_000 on 9/27/2016.
 */
public class GameGUI {

    private int width;
    private int height;

    private int boardWidth;
    private int boardHeight;

    private NumberGame game;

    private JFrame frame;
    private GamePanel boardPanel;
    private JPanel buttonPanel;

    public GameGUI() {
        width = 800;
        height = 600;
        boardWidth = 4;
        boardHeight = 4;

        game = new NumberGame();

        frame = new JFrame();
        boardPanel = new GamePanel(4, 4, game);
        buttonPanel = new JPanel();
    }

    public void init() {
        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setTitle("1024 Revamped");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GameGUI graphicalUI = new GameGUI();
        graphicalUI.init();
    }

}
