package game1024;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kylef_000 on 9/20/2016.
 */
public class NumberGame implements NumberSlider {

    private ArrayList<Cell> cells;
    private int width;
    private int height;
    private int winningValue;
    private int[][] boardValues;
    private boolean[][] hasMerged;

    private final boolean debug = false;

    private ArrayList<ArrayList<Cell>> savedBoards;

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        this.width = width;
        this.height = height;
        this.winningValue = winningValue;
        this.boardValues = new int[height][width];
        this.hasMerged = new boolean[height][width];
        this.cells = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }

        // testing values
        if (debug) {
            boardValues[0][0] = 8;
            boardValues[0][1] = 2;
            boardValues[1][0] = 4;
//            boardValues[0][3] = 1;
        }

    }

    @Override
    public void reset() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardValues[i][j] = 0;
                hasMerged[i][j] = false;
            }
        }
    }

    @Override
    public void setValues(int[][] ref) {

    }

    @Override
    public Cell placeRandomValue() {

        Random random = new Random();

        int r = random.nextInt(3);
        int c = random.nextInt(3);

        while (boardValues[r][c] != 0) {
            r = random.nextInt(3);
            c = random.nextInt(3);
        }


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

//    @Override
    public boolean slide2(SlideDirection dir) {

        // need to get it to slide indefinitely to the desired direction until tiles cannot merge
        // really need to create helper methods to determine if a move/merge is possible
        // j is x
        // i is y

        if (dir == SlideDirection.UP) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (boardValues[i][j] != 0) {
                        if (i > 0) {

                            int y = i;

                            while (y > 0) {

                                int val = boardValues[y][j];

                                if (val == boardValues[y-1][j]) {
                                    boardValues[y][j] = 0;
                                    boardValues[y - 1][j] = val*2;
                                } else {
                                    if (boardValues[y-1][j] == 0) {
                                        boardValues[y][j] = 0;
                                        boardValues[y-1][j] = val;
                                    }
                                }

                                y--;
                            }

                        }
                    }
                }
            }

        } else if (dir == SlideDirection.DOWN) {

            for (int i = height-1; i >= 0; i--) {
                for (int j = width-1; j >= 0; j--) {
                    if (boardValues[i][j] != 0) {
                        if (i < height-1) {

                            int y = i;

                            while (y < height-1) {

                                int val = boardValues[y][j];

                                if (val == boardValues[y+1][j]) {
                                    boardValues[y][j] = 0;
                                    boardValues[y+1][j] = val*2;
                                } else {
                                    if (boardValues[y+1][j] == 0) {
                                        boardValues[y][j] = 0;
                                        boardValues[y+1][j] = val;
                                    }
                                }

                                y++;
                            }

                        }
                    }
                }
            }

        } else if (dir == SlideDirection.LEFT) {

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (boardValues[i][j] != 0) {
                        if (j > 0) {

                            int x = j;

                            while (x > 0) {

                                int val = boardValues[i][x];

                                if (val == boardValues[i][x-1]) {
                                    boardValues[i][x] = 0;
                                    boardValues[i][x-1] = val*2;
                                } else {
                                    if (boardValues[i][x-1] == 0) {
                                        boardValues[i][x] = 0;
                                        boardValues[i][x-1] = val;
                                    }
                                }

                                x--;
                            }

                        }
                    }
                }
            }

        } else if (dir == SlideDirection.RIGHT) {

            for (int i = width-1; i >= 0; i--) {
                for (int j = height-1; j >= 0; j--) {
                    if (boardValues[i][j] != 0) {
                        if (j < height-1) {

                            int x = j;

                            while (x < width-1) {

                                int val = boardValues[i][x];

                                if (val == boardValues[i][x+1]) {
                                    boardValues[i][x] = 0;
                                    boardValues[i][x+1] = val*2;
                                } else {
                                    if (boardValues[i][x+1] == 0) {
                                        boardValues[i][x] = 0;
                                        boardValues[i][x+1] = val;
                                    }
                                }

                                x++;
                            }

                        }
                    }
                }
            }

        }

        // if any tile of any sort has moved on the board, placeRandomValue()
        if (!debug) {
            placeRandomValue();
        }

        return false;
    }

    @Override
    public boolean slide(SlideDirection dir) {

        boolean didSlide = false;

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

                        while (y < width-1) {

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
                                System.out.println("MERGE");
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
        }

        /* We reset merges every slide */
        resetMerges();

        return false;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {

        ArrayList<Cell> temp = new ArrayList<Cell>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardValues[i][j] != 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    @Override
    public GameStatus getStatus() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardValues[i][j] == winningValue) {
                    return GameStatus.USER_WON;
                }
            }
        }

        // TODO - Account for if two 1 tiles are next to each other
        if (getNonEmptyTiles().size() == (width * height)) {
            return GameStatus.USER_LOST;
        } else {
            return GameStatus.IN_PROGRESS;
        }

    }

    @Override
    public void undo() {

    }

    private ArrayList<Cell> getEmptyTiles() {
        ArrayList<Cell> temp = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardValues[i][j] == 0) {
                    temp.add(new Cell(i, j, boardValues[i][j]));
                }
            }
        }

        return temp;
    }

    private void saveBoard() {
        savedBoards.add(cells);
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
