package game1024;

import java.util.ArrayList;
import java.util.Random;

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

    private final boolean debug = false;

    private ArrayList<int[][]> savedBoards;

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        this.width = width;
        this.height = height;
        this.winningValue = winningValue;
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.currentStatus = GameStatus.IN_PROGRESS;
        this.cells = new ArrayList<>();
        this.savedBoards = new ArrayList<>();

        System.out.println(width + " | " + height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        // testing values
        if (debug) {
            boardValues[3][0] = 1;
            boardValues[3][1] = 1;
            boardValues[3][0] = 1;
            boardValues[3][3] = 1;
        }

    }

    @Override
    public void reset() {
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.cells = new ArrayList<>();

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

        if (didSlide && !debug) {
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
            int[][] prevBoard = savedBoards.get(savedBoards.size() - 2);

            setValues(prevBoard);

            savedBoards.remove(savedBoards.size() - 1);
        } else {
            throw new IllegalStateException();
        }

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

        savedBoards.add(temp);
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
