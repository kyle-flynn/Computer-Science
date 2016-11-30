package project4;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
	public DVD(GregorianCalendar rentedOn, GregorianCalendar dueBack,
               String title, String name) {
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

        /** Creating date objects that have time reset so that we can
         compare their month, day, and year only. */
        Date todayDate = zeroOutTime(today.getTime());
        Date dueDate = zeroOutTime(getDueBack().getTime());

        /** Storing our compared result into an integer. */
        int result = todayDate.compareTo(dueDate);

        System.out.println("COMPARING DATES: " + todayDate.compareTo(dueDate));

        /* If the result is 1, then today's date is greater than the
        due date, therefor the return is late. */
        if (result == 1) {
            return true;
        } else {
            return false;
        }
	}

    /*****************************************************************
     Private helper method that zeros out the time of a given date. We
     zero our the time so that we properly compare the year, month, and
     day of a given date.
     @param date The date that will be edited to have no time.
     @return The modified date with a zeroed-out time.
     *****************************************************************/
    private Date zeroOutTime(Date date) {

        /** Creating an instance of a calendar. */
        Calendar calendar = Calendar.getInstance();

        // Setting the calendar's time, and resetting it's parameters.
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Return the calendar's time as a Date object.
        return calendar.getTime();
    }

}
