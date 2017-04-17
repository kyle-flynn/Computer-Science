package game1024;

import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
import game1024.Cell;


/************************************************************************************************
 * Methods for the game of 2048 to be implemented by TextUI where two like numbers starting
 * at two are added until a goal number is achieved by the player.
 *
 * @author Brittany Richards
 *
 * @version 1.0
 ************************************************************************************************/
public class NumberGame implements NumberSlider{

	private ArrayList <Cell> cells;

	/** The height of the game board */
	private int height;

	/** The width of the game board */
	private int width;

	/** The value needed to win the game */
	private int winningValue;

	/** Variable to check progress */
	private GameStatus currentStatus;

	/** Internal 2D array */
	private int [][] board;

	/** Stack Board */
	private Stack <int [][]> aBoard;

	/** If a tile has merged */
	private boolean[][] wasMerged;


    /********************************************************************************************
     * Reset the game logic to handle a board of a given dimension
     *
     * @param height the number of rows in the board
     * @param width the number of columns in the board
     * @param winningValue the value that must appear on the board to
     *                     win the game
     * @throws IllegalArgumentException when the winning value is not power of two
     *  or negative
     ********************************************************************************************/
	@Override
	public void resizeBoard(int height, int width, int winningValue) {

		/* throws if board has less than 2 columns, rows, and winning value */
		if (height < 0 || width < 0 || winningValue < 1) throw (new IllegalArgumentException ());
		this.height = height;
		this.width = width;
		this.winningValue = winningValue;
		this.board = new int [height][width];
		this.wasMerged = new boolean [height][width];
		this.cells = new ArrayList <> ();
		this.aBoard = new Stack<>();

		/* Resets board values */
		for (int i = 0; i < height; i++){
			for (int t = 0; t < width; t++){
				board [i][t] = 0;
				wasMerged[i][t] = false;
			}
		}
	}


	/********************************************************************************************
     * Removes all numbered tiles from the board and place
     * TWO non-zero values at random location
     ********************************************************************************************/
	@Override
	public void reset() {
		/* Resetting arrays and stack for a new game */
		this.board = new int [height][width];
		this.wasMerged = new boolean[height][width];
		this.cells.clear();
		this.aBoard.clear();

		/* Resets board values */
		for (int i = 0; i < height; i++){
			for (int t = 0; t < width; t++){
				board [i][t] = 0;
				wasMerged[i][t] = false;
			}
		}

		/* Start board with two random new values */
		placeRandomValue();
		placeRandomValue();

		/* To Pass Test */
		this.currentStatus = GameStatus.IN_PROGRESS;

		/* Adds the very first board to stack */
		aBoard.push(board);
	}


	/********************************************************************************************
     * Sets the game board to the desired values given in the 2D array.
     * @param ref
     ********************************************************************************************/
	@Override
	public void setValues(int[][] ref) {
		for(int i = 0; i < height; i++){
			for(int t = 0; t< width; t++){

				/* Transfers the value of the given 2D array to the internal array */
				int tran = ref[i][t];
				board[i][t] = tran;
			}
		}
	}


    /********************************************************************************************
     * Insert one random tile into an empty spot on the board.
     *
     * @return a Cell object with its row, column, and value attributes
     *  initialized properly
     *
     * @throws IllegalStateException when the board has no empty cell
     ********************************************************************************************/
	@Override
	public Cell placeRandomValue() {

		Random random = new Random();

		/** Gets a random empty cell using a helper method. This is
		 fool proof and won't bottom-out searching for a cell. */
		Cell randomCell = getEmptyTiles().get(
				random.nextInt(getEmptyTiles().size()));

		/** Grabbing the row and column of the random cell. */
		int r = randomCell.row;
		int c = randomCell.column;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (r == i && c == j) {

					/** Ternary function that determines if the
					 number should be a one or a two. We go to ten
					 to add variance. */
					int val = (((random.nextInt(9) + 1)/2) == 1 ? 1 : 2);
					board[r][c] = val;

					/** Returns the newly created cell. */
					return new Cell(r, c, val);
				}
			}
		}

		return null;
	}


	/********************************************************************************************
     * Slide all the tiles in the board in the requested direction
     * @param dir move direction of the tiles
     *
     * @return true when the board changes
     ********************************************************************************************/
	@Override
	public boolean slide(SlideDirection dir) {

		/* Used at the end to tell whether tiles were moved */
        boolean slid = false;

        /* A -1 shows moving up/left in the array where 1 shows moving down/right in the array */
        if (dir == SlideDirection.UP) {
            if (moveUpDown(-1)) {
                slid = true;
            }
        } else if (dir == SlideDirection.DOWN) {
            if (moveUpDown(1)) {
                slid = true;
            }
        } else if (dir == SlideDirection.LEFT) {
            if (moveLeftRight(-1)) {
                slid = true;
            }
        } else if (dir == SlideDirection.RIGHT) {
            if (moveLeftRight(1)) {
                slid = true;
            }
        }

        /* Place random cell, and save board if a slide occurred */
        if (slid) {
            placeRandomValue();
            aBoard.push(board);
        }

        /* Reset merges every slide */
        resetMerge();

        return slid;
	}


	/********************************************************************************************
     * Helper method that allows movement left and right.
     * @param dir determines whether we are sliding left or right
     *
     * @return true if tiles are moved, otherwise returns false
     ********************************************************************************************/
	private boolean moveLeftRight(int dir){

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
				if (board[i][j] != 0) {

					/** Re-assigning our 'i' value to a 'y' value for manipulation. */
					int x = j;


					/** This while loop loops right/left, depending on
					 direction, and checks the tiles on each side, and
					 if a move/merge can occur, for wherever the x
					 variable is. */
					while (dir == -1 ? x > 0 : x < width-1) {

						if (ableMerge(i, x, i, x+dir)) {
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


	/********************************************************************************************
     * Helper method that allows movement up and down
     * @param dir determines whether we are sliding up or down
     *
     * @return true if tiles are moved, otherwise returns false
     ********************************************************************************************/
	private boolean moveUpDown(int dir){

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
				if (board[i][j] != 0) {

					/** Re-assigning our 'i' value to a 'y' value for manipulation. */
					int y = i;

					/** This while loop loops down/up, depending on
					 direction, and checks the tiles above or below, and
					 if a move/merge can occur, for wherever the y
					 variable is. */
					while (dir == -1 ? y > 0 : y < height-1) {

						if (ableMerge(y, j, y+dir, j)) {
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



    /********************************************************************************************
    * Obtains tiles that contain values
    * @return an arraylist of Cells. Each cell holds the (row,column) and
    * value of a tile
    *********************************************************************************************/
	@Override
	public ArrayList<Cell> getNonEmptyTiles() {

		/** Temporary ArrayList to store values. */
		ArrayList<Cell> temp = new ArrayList<>();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				/** Checks if the tile isn't 0, and adds it. */
				if (board[i][j] != 0) {
					temp.add(new Cell(i, j, board[i][j]));
				}
			}
		}

		return temp;
	}


	/*********************************************************************************************
	 * Obtains empty tiles
	 * @return an arraylist of Cells with the coordinates of the empty tiles
	 *********************************************************************************************/
	public ArrayList<Cell> getEmptyTiles (){

		/** Temporary ArrayList to store values. */
		ArrayList<Cell> temp = new ArrayList<>();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				/** Checks if boardValues are 0 */
				if (board[i][j] == 0) {
					temp.add(new Cell(i, j, board[i][j]));
				}
			}
		}

		return temp;
	}


    /*********************************************************************************************
     * Return the current state of the game
     * @return one of the possible values of GameStatus enum
     *********************************************************************************************/
	@Override
	public GameStatus getStatus() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                /** If boardValue is the winning value, you've won! */
                if (board[i][j] == winningValue) {

                    /** We will be setting the currentStatus every
                    return so that we can use it in later methods */
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
                        if (ableMerge(i, j, i-1, j)) {
                            currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (i < height-1) {

                            /** Checks if the tile below can be merged */
                            if (ableMerge(i, j, i+1, j)) {
                                currentStatus = GameStatus.IN_PROGRESS;
                                return currentStatus;
                            }
                        }
                    }

                    if (j > 0) {

                        /** Checks if tile to the right can merge. */
                        if (ableMerge(i, j, i, j-1)) {
                            currentStatus = GameStatus.IN_PROGRESS;
                            return currentStatus;
                        }

                        if (j < width-1) {

                            /** Checks if tile to the left can merge. */
                            if (ableMerge(i, j, i, j+1)) {
                                currentStatus = GameStatus.IN_PROGRESS;
                                return currentStatus;
                            }
                        }
                    }

                }
            }

            currentStatus = GameStatus.USER_LOST;
            return currentStatus;
        } else {
            currentStatus = GameStatus.IN_PROGRESS;
            return currentStatus;
        }
    }



    /*****************************************************************
    Method that sets the winning value. This is public so
    that the GUI can access it as well.
    *****************************************************************/
    public void setWinningValue(int winningValue) {
        this.winningValue = winningValue;
        getStatus();
    }



    /*********************************************************************************************
     * Undo the most recent action, i.e. restore the board to its previous
     * state. Calling this method multiple times will ultimately restore
     * the game to the very first initial state of the board holding two
     * random values. Further attempt to undo beyond this state will throw
     * an IllegalStateException.
     *
     * @throws IllegalStateException when undo is not possible
     *********************************************************************************************/
	@Override
	public void undo() {
		if(aBoard.size() > 1){
			aBoard.pop();
			aBoard.pop();
		}
		else{
			throw new IllegalStateException();
		}
	}


	/*********************************************************************************************
     * Resets the 2D array of isMerged for all tiles.
     *********************************************************************************************/
	private void resetMerge(){
		for (int i = 0; i < height; i++) {
            for (int t = 0; t < width; t++) {
                wasMerged[i][t] = false;
            }
        }
	}


	/*********************************************************************************************
     * Merges two, same-value, cells together to form a new one.
     *********************************************************************************************/
	private void merge(int i, int t, int i2, int t2){
		/* Sets the new tile value, which is double what the tile was */
		board[i2][t2] = board[i][t] * 2;
		board[i][t] = 0;

		/* Sets isMerged to true */
		wasMerged[i2][t2] = true;
	}


	/*********************************************************************************************
     * Checks to see if two adjacent tiles are able to merge together.
     * @return true if the values are the same, non-zero, numbers. Otherwise, returns false
     *********************************************************************************************/
	private boolean ableMerge(int i1, int j1, int i2, int j2){

		/** We have to make sure the tiles are both zero. */
		boolean notZero = board[i1][j1] != 0 &&
				board[i2][j2] != 0;

		/** We have to make sure they are the same number tile. */
		boolean sameTile = board[i1][j1] == board[i2][j2];

		/** At least one tile must not have merged current slide. */
		boolean didMerge = wasMerged[i1][j1] || wasMerged[i2][j2];

		return sameTile && !didMerge && notZero;
	}


	/*********************************************************************************************
     * Checks if a tile can move into an adjacent space
     * @return true if the current tile is not zero and if the other tile is not empty.
     * Otherwise, returns false.
     *********************************************************************************************/
	private boolean canMove(int i, int t, int i2, int t2){
		if (board[i][t] != 0 && board[i2][t2] == 0) return true;
		else return false;
	}


	/*********************************************************************************************
     * Moves selected tile into an unoccupied space.
     *********************************************************************************************/
	private void move(int i, int t, int i2, int t2){
		board[i2][t2] = board[i][t];
		board[i][t] = 0;
	}
}
