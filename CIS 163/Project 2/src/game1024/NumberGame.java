package game1024;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by kylef_000 on 9/20/2016.
 */
public class NumberGame implements NumberSlider {

    private ArrayList<Cell> cells;

    private GameStatus currentStatus;
    private int width;
    private int height;
    private int winningValue;
    private int[][] boardValues;
    private boolean[][] hasMerged;

    private int score;
    private int highScore;

    private Stack<int[][]> savedBoards;
    private Stack<Integer> savedScores;

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        this.width = width;
        this.height = height;
        this.winningValue = winningValue;
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.currentStatus = GameStatus.IN_PROGRESS;
        this.cells = new ArrayList<>();
        this.savedBoards = new Stack<>();
        this.savedScores = new Stack<>();
        this.score = 0;
        this.highScore = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        loadHighScore();

    }

    @Override
    public void reset() {
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.cells.clear();
        this.savedBoards.clear();
        this.savedScores.clear();
        this.score = 0;

        saveHighScore();
        loadHighScore();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        this.currentStatus = GameStatus.IN_PROGRESS;

        placeRandomValue();
        placeRandomValue();
    }

    @Override
    public void setValues(int[][] ref) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.boardValues[i][j] = ref[i][j];
                if (boardValues[i][j] != 0) {
                }
            }
        }
    }

    @Override
    public Cell placeRandomValue() {

        Random random = new Random(System.currentTimeMillis());

        Cell randomCell = getEmptyTiles().get(random.nextInt(getEmptyTiles().size()));

        int r = randomCell.row;
        int c = randomCell.column;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (boardValues[i][j] == 0) {
                    if (r == i && c == j) {
                        int val = (((random.nextInt(9) + 1)/2) == 1 ? 1 : 2);
                        boardValues[r][c] = val;
                        return new Cell(r, c, val);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        boolean didSlide = false;

        if (savedBoards.size() == 0) {
            saveBoard();
        }

        if (dir == SlideDirection.UP) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    /* We don't want to slide empty tiles */
                    if (boardValues[i][j] != 0) {

                        /* Re-assigning our 'i' value to a 'y' value for manipulation. */
                        int y = i;

                        while (y > 0) {

                            if (canMerge(y, j, y-1, j)) {
                                merge(y, j, y-1, j);
                                didSlide = true;
                            }

                            if (canMove(y, j, y-1, j)) {
                                move(y, j, y-1, j);
                                didSlide = true;
                            }

                            y--;
                        }

                    }

                }
            }

        } else if (dir == SlideDirection.DOWN) {

            for (int i = height-1; i >= 0; i--) {
                for (int j = width-1; j >= 0; j--) {

                    /* We don't want to slide empty tiles */
                    if (boardValues[i][j] != 0) {

                        /* Re-assigning our 'i' value to a 'y' value for manipulation. */
                        int y = i;

                        while (y < height-1) {

                            if (canMerge(y, j, y+1, j)) {
                                merge(y, j, y+1, j);
                                didSlide = true;
                            }

                            if (canMove(y, j, y+1, j)) {
                                move(y, j, y+1, j);
                                didSlide = true;
                            }

                            y++;
                        }

                    }

                }
            }

        } else if (dir == SlideDirection.LEFT) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    /* We don't want to slide empty tiles */
                    if (boardValues[i][j] != 0) {

                        /* Re-assigning our 'j' value to a 'x' value for manipulation. */
                        int x = j;

                        while (x > 0) {

                            if (canMerge(i, x, i, x-1)) {
                                merge(i, x, i, x-1);
                                didSlide = true;
                            }

                            if (canMove(i, x, i, x-1)) {
                                move(i, x, i, x-1);
                                didSlide = true;
                            }

                            x--;
                        }

                    }

                }
            }

        } else if (dir == SlideDirection.RIGHT) {

            for (int i = height-1; i >= 0; i--) {
                for (int j = width-1; j >= 0; j--) {

                    /* We don't want to slide empty tiles */
                    if (boardValues[i][j] != 0) {

                        /* Re-assigning our 'j' value to a 'x' value for manipulation. */
                        int x = j;

                        while (x < width-1) {

                            if (canMerge(i, x, i, x+1)) {
                                merge(i, x, i, x+1);
                                didSlide = true;
                            }

                            if (canMove(i, x, i, x+1)) {
                                move(i, x, i, x+1);
                                didSlide = true;
                            }

                            x++;
                        }

                    }

                }
            }

        }

        if (didSlide) {
            placeRandomValue();
            saveBoard();
        }

        /* We reset merges every slide */
        resetMerges();

        return didSlide;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {

        ArrayList<Cell> temp = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (boardValues[i][j] != 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    @Override
    public GameStatus getStatus() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (boardValues[i][j] == winningValue) {
                    this.currentStatus = GameStatus.USER_WON;
                    return currentStatus;
                }
            }
        }

        if (getNonEmptyTiles().size() == (width * height)) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    if (i > 0) {
                        if (canMerge(i, j, i-1, j)) {
                            this.currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (i < height-1) {
                            if (canMerge(i, j, i+1, j)) {
                                this.currentStatus = GameStatus.IN_PROGRESS;
                                return currentStatus;
                            }
                        }
                    }

                    if (j > 0) {
                        if (canMerge(i, j, i, j-1)) {
                            this.currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (j < width-1) {
                            if (canMerge(i, j, i, j+1)) {
                                this.currentStatus = GameStatus.IN_PROGRESS;
                                return currentStatus;
                            }
                        }
                    }

                }
            }

            this.currentStatus = GameStatus.USER_LOST;
            return currentStatus;
        } else {
            this.currentStatus = GameStatus.IN_PROGRESS;
            return currentStatus;
        }

    }

    @Override
    public void undo() {

        if (savedBoards.size() > 1) {

            savedBoards.pop();
            savedScores.pop();

            int[][] prevBoard = savedBoards.pop();
            int prevScore = savedScores.pop();

            setValues(prevBoard);
            score = prevScore;
        } else {
            throw new IllegalStateException();
        }

    }

    public void loadHighScore() {
        try {

            /** Loads the file through scanner. */
            Scanner fileReader = new Scanner(new File("highscore.txt"));

            /** Splits our data by '/' */
            int highScore = fileReader.nextInt();

            this.highScore = highScore;

            fileReader.close();
        } catch(Exception error) {
            System.out.println("Error retrieving high score.");
            this.highScore = 0;
        }
    }

    public void saveHighScore() {
        /** How we will write to a file. Starts null. */
        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt")));
            out.println(this.highScore);

            /** Must close the stream to prevent memory leaks */
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing out to file.");
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    private ArrayList<Cell> getEmptyTiles() {

        ArrayList<Cell> temp = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (boardValues[i][j] == 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    private void saveBoard() {

        int[][] temp = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp[i][j] = boardValues[i][j];
            }
        }

        savedBoards.push(temp);
        savedScores.push(score);
    }

    private void resetMerges() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hasMerged[i][j] = false;
            }
        }
    }

    private void merge(int i1, int j1, int i2, int j2) {
        boardValues[i2][j2] = boardValues[i1][j1] * 2;
        boardValues[i1][j1] = 0;
        hasMerged[i2][j2] = true;
        score+= boardValues[i2][j2];

        if (score > highScore) {
            this.highScore = score;
        }
    }

    private void move(int i1, int j1, int i2, int j2) {
        boardValues[i2][j2] = boardValues[i1][j1];
        boardValues[i1][j1] = 0;
    }

    private boolean canMerge(int i1, int j1, int i2, int j2) {
        boolean notZero = boardValues[i1][j1] != 0 && boardValues[i2][j2] != 0;
        boolean sameTile = boardValues[i1][j1] == boardValues[i2][j2];
        boolean didMerge = hasMerged[i1][j1] || hasMerged[i2][j2];
        return sameTile && !didMerge && notZero;
    }

    private boolean canMove(int i1, int j1, int i2, int j2) {
        return boardValues[i1][j1] != 0 && boardValues[i2][j2] == 0;
    }

}
