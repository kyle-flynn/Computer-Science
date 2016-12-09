package project4;

import java.util.GregorianCalendar;

/*****************************************************************
 Game class that extends the DVD class. With the Game class you may
 determine the console/player that the Game was rented for.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class Game extends DVD {

	/** Enum instance of the different types of players. */
	private PlayerType player;

    /*****************************************************************
     Default constructor that creates a blank DVD object.
     *****************************************************************/
	public Game() {
		super();
	}

    /*****************************************************************
     Constructor that creates a DVD and initializes the player type.
     *****************************************************************/
	public Game(GregorianCalendar rentedOn, GregorianCalendar dueBack,
			String title, String name, PlayerType player) {
		super(rentedOn, dueBack, title, name);
		this.player = player;
	}

    /*****************************************************************
     Constructor that creates a DVD and initializes only the player
     type.
     *****************************************************************/
	public Game(PlayerType player) {
		this.player = player;
	}

    /*****************************************************************
     Overriden method that gets the cost determined by whether nor not
     the Game is late.
     @return The cost of the return as a double.
     *****************************************************************/
	@Override
	public double getCost(GregorianCalendar dat) {
		return !isLate(dat) ? 5 : 10;
	}

    /*****************************************************************
     Getter method that returns the player/console the game was rented
     for.
     @return The player that the game was rented for as a PlayerType
     enum.
     *****************************************************************/
	public PlayerType getPlayer() {
		return player;
	}

    /*****************************************************************
     Method that sets the given player/console for the game.
     *****************************************************************/
	public void setPlayer(PlayerType player) {
		this.player = player;
	}


}
