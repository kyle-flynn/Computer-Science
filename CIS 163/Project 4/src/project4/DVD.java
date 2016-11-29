package project4;

import java.io.Serializable;
import java.util.GregorianCalendar;

/*****************************************************************
 DVD class that holds the required information.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class DVD implements Serializable {

    /** Instance of the date that the DVD was rented on. */
	protected GregorianCalendar rentedOn;

    /** Instance of the date that the DVD was due back. */
	protected GregorianCalendar dueBack;

    /** The title of the rental. */
	protected String title;

    /** the name of the renter who rented the DVD. */
	protected String nameOfRenter;

    /*****************************************************************
     Default constructor that does nothing so that a blank DVD may be
     instantiated.
     *****************************************************************/
	public DVD() {
	}

    /*****************************************************************
     Constructor that intializes a DVD with all of the specified
     parameters.
     *****************************************************************/
	public DVD(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name) {
		super();
		this.rentedOn = rentedOn;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
	}

    /*****************************************************************
     Getter method that gets the cost determined by whether nor not
     the DVD is late.
     @return The cost of the return as a double.
     *****************************************************************/
    public double getCost(GregorianCalendar dat) {
        return !isLate(dat) ? 1.2 : 2;
    }

    /*****************************************************************
     Getter method that gets the date that the DVD was rented on.
     @return The date of return as a GregorianCalendar object.
     *****************************************************************/
	public GregorianCalendar getRentedOn() {
		return rentedOn;
	}

    /*****************************************************************
     Method that sets the date that the DVD was rented on.
     *****************************************************************/
	public void setRentedOn(GregorianCalendar opened) {
		this.rentedOn = opened;
	}

    /*****************************************************************
     Getter method that gets the date that the DVD was due back.
     @return The due date as a GregorianCalendar object.
     *****************************************************************/
	public GregorianCalendar getDueBack() {
		return dueBack;
	}

    /*****************************************************************
     Method that sets the date that the DVD was due back.
     *****************************************************************/
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}

    /*****************************************************************
     Getter method that returns the title of the DVD.
     @return The title of the DVD as a String.
     *****************************************************************/
	public String getTitle() {
		return title;
	}

    /*****************************************************************
     Method that sets the title of the DVD.
     *****************************************************************/
	public void setTitle(String title) {
		this.title = title;
	}

    /*****************************************************************
     Getter method that returns the renter's name of the DVD.
     @return The renter's name as a String.
     *****************************************************************/
	public String getNameOfRenter() {
		return nameOfRenter;
	}

    /*****************************************************************
     Method that sets the renter's name of the DVD.
     *****************************************************************/
	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

    /*****************************************************************
     Protected method that determines whether or not the return of the
     DVD is late based on today's date.
     @return true of the DVD is late, false if it is not late.
     *****************************************************************/
	protected boolean isLate(GregorianCalendar today) {

        /** Variables that get the year, month, and day of the due date. */
		int yearDue = getDueBack().get(GregorianCalendar.YEAR);
		int monthDue = getDueBack().get(GregorianCalendar.MONTH) + 1;
		int dayDue = getDueBack().get(GregorianCalendar.DAY_OF_MONTH);

        /** Variables that get the year, month, and day of today's date. */
        String yearTodayRaw = today.get(GregorianCalendar.YEAR) + "";
        int yearToday = Integer.parseInt(yearTodayRaw.substring(2));
        int monthToday = today.get(GregorianCalendar.MONTH) + 1;
        int dayToday = today.get(GregorianCalendar.DAY_OF_MONTH);

        if (yearToday > yearDue) {
            return true;
        }

        if (monthToday > monthDue) {
            return true;
        }

        if (dayToday > dayDue) {
            return true;
        }

        if (yearToday == yearDue && monthToday == monthDue && dayToday == dayDue) {
            return false;
        }

        return false;
	}

}
