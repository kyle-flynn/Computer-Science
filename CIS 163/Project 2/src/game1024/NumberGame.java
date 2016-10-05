package game1024;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/*****************************************************************
Number Game Logic.
@author Kyle Flynn
@version 1.0
*****************************************************************/
public class NumberGame implements NumberSlider {

    /** Holds useful data such as location, and value of a cell */
    private ArrayList<Cell> cells;

    /** Must be a variable for an IN_PROGRESS check later on */
    private GameStatus currentStatus;

    /** Holds the width of the board */
    private int width;

    /** Holds the height of the board */
    private int height;

    /** Holds the value required to win the game */
    private int winningValue;

    /** Holds all values of the board in a 2d array */
    private int[][] boardValues;

    /** Simple 2d array that tells if a tile has merged */
    private boolean[][] hasMerged;

    /** Holds the current score of the game */
    private int score;

    /** Holds the highest score completed in any session */
    private int highScore;

    /** Stack used to save boards after the user slides */
    private Stack<int[][]> savedBoards;

    /** Stack used to save scores after the user slides */
    private Stack<Integer> savedScores;

    /*****************************************************************
    Overriden method that sets all the necessary values up for play.
    @param height The height of the board.
    @param width The width of the board.
    @param winningValue The value required to win.
   *****************************************************************/
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

        /** Resetting board values */
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        /** Getting the high score from a pre-set file */
        loadHighScore();

    }

    /*****************************************************************
    Overriden method that resets the board for another session.
   *****************************************************************/
    @Override
    public void reset() {
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.cells.clear();
        this.savedBoards.clear();
        this.savedScores.clear();
        this.score = 0;

        /** High score is saved and then loaded on reset */
        saveHighScore();
        loadHighScore();

        /** Resetting board values */
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        /** This simple line passes a JUnit test! */
        this.currentStatus = GameStatus.IN_PROGRESS;

        placeRandomValue();
        placeRandomValue();
    }

    /*****************************************************************
    Overriden method that replaces the current board values.
    @param ref The 2d int array to set the board values to.
   *****************************************************************/
    @Override
    public void setValues(int[][] ref) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** Sets the 'this' board values to the reference */
                boardValues[i][j] = ref[i][j];
            }
        }
    }

    /*****************************************************************
    Overriden method that places a random value on a random empty
    cell.
    @return null if a Cell couldn't be placed. Returns the random
    Cell if it was placed.
   *****************************************************************/
    @Override
    public Cell placeRandomValue() {

        Random random = new Random();

        /** Gets a random empty cell using a helper method. This is
        fool proof and won't bottom-out searching for a cell. */
        Cell randomCell = getEmptyTiles().get(
                random.nextInt(getEmptyTiles().size()));

        /** Grabbing te row and coumn of the random cell. */
        int r = randomCell.row;
        int c = randomCell.column;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (r == i && c == j) {

                    /** Ternary function that determines if the
                    number should be a one or a two. We go to ten
                    to add variance. */
                    int val = (((random.nextInt(9) + 1)/2) == 1 ? 1 : 2);
                    boardValues[r][c] = val;

                    /** Returns the newly created cell. */
                    return new Cell(r, c, val);
                }
            }
        }

        return null;
    }

    /*****************************************************************
    Overriden method that slides the board in the given direction.
    cell.
    @return true if cells did slide, and false if they didn't.
    *****************************************************************/
    @Override
    public boolean slide(SlideDirection dir) {
        boolean didSlide = false;

        if (savedBoards.size() == 0) {
            saveBoard();
        }

        if (dir == SlideDirection.UP) {
            if (moveVertically(-1)) {
                didSlide = true;
            }
        } else if (dir == SlideDirection.DOWN) {
            if (moveVertically(1)) {
                didSlide = true;
            }
        } else if (dir == SlideDirection.LEFT) {
            if (moveHorizontally(-1)) {
                didSlide = true;
            }
        } else if (dir == SlideDirection.RIGHT) {
            if (moveHorizontally(1)) {
                didSlide = true;
            }
        }

        if (didSlide) {
            placeRandomValue();
            saveBoard();
        }

        /** We reset merges every slide */
        resetMerges();

        return didSlide;
    }

    /*****************************************************************
    Overriden method that gets tiles that aren't empty.
    cell.
    @return an ArrayList of type Cell of tiles that are not empty.
    *****************************************************************/
    @Override
    public ArrayList<Cell> getNonEmptyTiles() {

        /** Temporary ArrayList to store values. */
        ArrayList<Cell> temp = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** Checks if the tile isn't 0, and adds it. */
                if (boardValues[i][j] != 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    /*****************************************************************
    Overriden method that controls that status of the game.
    @return Current GameStatus (IN_PROGRESS, USER_LOST, USER_WON).
    *****************************************************************/
    @Override
    public GameStatus getStatus() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** If boardValue is the winning value, you've won! */
                if (boardValues[i][j] == winningValue) {
                    currentStatus = GameStatus.USER_WON;
                    return currentStatus;
                }
            }
        }

        /** Determines if the board is filled */
        if (getNonEmptyTiles().size() == (width * height)) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    if (i > 0) {

                        /** Checks if the tile above can be merged */
                        if (canMerge(i, j, i-1, j)) {
                            this.currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (i < height-1) {

                            /** Checks if the tile below can be merged */
                            if (canMerge(i, j, i+1, j)) {
                                this.currentStatus = GameStatus.IN_PROGRESS;
                                return currentStatus;
                            }
                        }
                    }

                    if (j > 0) {

                        /** Checks if tile to the right can merge. */
                        if (canMerge(i, j, i, j-1)) {
                            this.currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (j < width-1) {

                            /** Checks if tile to the left can merge. */
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

    /*****************************************************************
    Overriden method that undos the last move the player made.
    *****************************************************************/
    @Override
    public void undo() {

        if (savedBoards.size() > 1) {

            /** We have to pop() twice because the top is the current
            save and the next one is the save we want. */
            savedBoards.pop();
            savedScores.pop();

            /** Previous board values. */
            int[][] prevBoard = savedBoards.pop();

            /** The previous score is also saved. */
            int prevScore = savedScores.pop();

            /** Setting the board values */
            setValues(prevBoard);
            score = prevScore;
        } else {
            throw new IllegalStateException();
        }

    }

    /*****************************************************************
    Method that loads the highest score from a file.
    *****************************************************************/
    public void loadHighScore() {
        try {

            /** Loads the file through scanner. */
            Scanner fileReader = new Scanner(new File("highscore.txt"));

            /** The only data in the file is the highscore. */
            int highScore = fileReader.nextInt();

            this.highScore = highScore;

            fileReader.close();
        } catch(Exception error) {
            System.out.println("Error retrieving high score.");
            this.highScore = 0;
        }
    }

    /*****************************************************************
    Method that saves the highscore into a file.
    *****************************************************************/
    public void saveHighScore() {
        /** How we will write to a file. Starts null. */
        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(
                    new FileWriter("highscore.txt")));
            out.println(this.highScore);

            /** Must close the stream to prevent memory leaks */
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing out to file.");
        }
    }

    /*****************************************************************
    Method that returns the curent score of the game.
    @return the current score as an int.
    *****************************************************************/
    public int getScore() {
        return score;
    }

    /*****************************************************************
    Method that returns the al-time high score.
    @return the high score as an int.
    *****************************************************************/
    public int getHighScore() {
        return highScore;
    }

    /*****************************************************************
    Method that sets the winning value. Really is only used for the
    GUI end.
    *****************************************************************/
    public void setWinningValue(int winningValue) {
        this.winningValue = winningValue;
        getStatus();
    }

    /*****************************************************************
     Private helper method that controls tile movement vertically.
     @return true if tiles were moved, and false if no tiles moved.
     *****************************************************************/
    private boolean moveVertically(int dir) {

        /** The 'i' value we are iterating over */
        int i = 0;

        /** The 'j' value we are iterating over */
        int j = 0;

        /** Where the 'i' value will be starting */
        int iStart = 0;

        /** Where the 'j' value will be starting */
        int jStart = 0;

        /** Controls the return value and whether tiles were moved */
        boolean didSlide = false;

        /** Setting the variables appropriately based on direction */
        if (dir == 1) {
            iStart = height - 1;
            jStart = width - 1;
        } else {
            iStart = 0;
            jStart = 0;
        }

        for (i = iStart; dir == 1 ? i >= 0 : i < height; i-=dir) {
            for (j = jStart; dir == 1 ? j >= 0 : j < width; j-=dir) {

                /** We don't want to slide empty tiles */
                if (boardValues[i][j] != 0) {

                    /** Re-assigning our 'i' value to a 'y' value for manipulation. */
                    int y = i;

                    while (dir == -1 ? y > 0 : y < height-1) {

                        if (canMerge(y, j, y+dir, j)) {
                            merge(y, j, y+dir, j);
                            didSlide = true;
                        }

                        if (canMove(y, j, y+dir, j)) {
                            move(y, j, y+dir, j);
                            didSlide = true;
                        }

                        y+=dir;
                    }

                }

            }
        }

        return didSlide;
    }

    /*****************************************************************
     Private helper method that controls tile movement horizontally.
     @return true if tiles were moved, and false if no tiles moved.
     *****************************************************************/
    private boolean moveHorizontally(int dir) {

        /** The 'i' value we are iterating over */
        int i = 0;

        /** The 'j' value we are iterating over */
        int j = 0;

        /** Where the 'i' value will be starting */
        int iStart = 0;

        /** Where the 'j' value will be starting */
        int jStart = 0;

        /** Controls the return value and whether tiles were moved */
        boolean didSlide = false;

        /** Setting the variables appropriately based on direction */
        if (dir == 1) {
            iStart = height - 1;
            jStart = width - 1;
        } else {
            iStart = 0;
            jStart = 0;
        }

        for (i = iStart; dir == 1 ? i >= 0 : i < height; i-=dir) {
            for (j = jStart; dir == 1 ? j >= 0 : j < width; j-=dir) {

                /** We don't want to slide empty tiles */
                if (boardValues[i][j] != 0) {

                    /** Re-assigning our 'i' value to a 'y' value for manipulation. */
                    int x = j;

                    while (dir == -1 ? x > 0 : x < width-1) {

                        if (canMerge(i, x, i, x+dir)) {
                            merge(i, x, i, x+dir);
                            didSlide = true;
                        }

                        if (canMove(i, x, i, x+dir)) {
                            move(i, x, i, x+dir);
                            didSlide = true;
                        }

                        x+=dir;
                    }

                }

            }
        }

        return didSlide;
    }

    /*****************************************************************
    Method that gets all tiles that are 0, or empty.
    @return an ArrayList of type Cell that are equal to 0.
    *****************************************************************/
    private ArrayList<Cell> getEmptyTiles() {

        ArrayList<Cell> temp = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** Checks if boardValues are 0 */
                if (boardValues[i][j] == 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    /*****************************************************************
    Method that saves the current board into the savedBoards stack.
    *****************************************************************/
    private void saveBoard() {

        /** Temporary 2d Integer array. */
        int[][] temp = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp[i][j] = boardValues[i][j];
            }
        }

        /** Pushing the board and score to respective Stacks. */
        savedBoards.push(temp);
        savedScores.push(score);
    }

    /*****************************************************************
    Method that resets the hasMerged 2d boolean array.
    *****************************************************************/
    private void resetMerges() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hasMerged[i][j] = false;
            }
        }
    }

    /*****************************************************************
    Method that merges two cells together to create a new one.
    *****************************************************************/
    private void merge(int i1, int j1, int i2, int j2) {
        boardValues[i2][j2] = boardValues[i1][j1] * 2;
        boardValues[i1][j1] = 0;
        hasMerged[i2][j2] = true;
        score+= boardValues[i2][j2];

        if (score > highScore) {
            this.highScore = score;
        }
    }

    /*****************************************************************
    Method that moves a tile into another space.
    *****************************************************************/
    private void move(int i1, int j1, int i2, int j2) {
        boardValues[i2][j2] = boardValues[i1][j1];
        boardValues[i1][j1] = 0;
    }

    /*****************************************************************
    Method that checks if two tiles can merge.
    @return true if they are the same tile, have not merged in the
    same move, and if they are not 0. Returns false otherwise.
    *****************************************************************/
    private boolean canMerge(int i1, int j1, int i2, int j2) {
        boolean notZero = boardValues[i1][j1] != 0 &&
                            boardValues[i2][j2] != 0;
        boolean sameTile = boardValues[i1][j1] == boardValues[i2][j2];
        boolean didMerge = hasMerged[i1][j1] || hasMerged[i2][j2];
        return sameTile && !didMerge && notZero;
    }

    /*****************************************************************
    Method that checks if a tile can move into a given space.
    @return true if the current tile is not 0, and the goal
    tile is empty.
    *****************************************************************/
    private boolean canMove(int i1, int j1, int i2, int j2) {
        return boardValues[i1][j1] != 0 && boardValues[i2][j2] == 0;
    }

}
