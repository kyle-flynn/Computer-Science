package project1;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

/*****************************************************************
 GeoCountDownTimer Object.
 @author Kyle Flynn
 @version 1.1
 *****************************************************************/
public class GeoCountDownTimer {

    /** Java 8 class that tells if a month, year, and day are valid */
    private YearMonth yearMonth;

    /** Months to be set in the constructor and validated */
    private int months;

    /** Days to be set in the constructor and validated */
    private int days;

    /** Years to be set in the constructor and validated */
    private int years;

    /*****************************************************************
     Constructor that should never be called because our class should
     never be called with an empty constructor. In the rare case that
     it is, set the defaults to 0.
     *****************************************************************/
    private GeoCountDownTimer() {
        this(0, 0, 0);
    }

    /*****************************************************************
     Constructor that takes 3 ints and converts them into a valid date.
     @param months (required) Month of the GeoCountDownTimer.
     @param days (required) Day of the GeoCountDownTimer.
     @param years (required) Year of the GeoCountDownTimer.
     @throws IllegalArgumentException if the date is invalid
     ****************************************************************/
    public GeoCountDownTimer(int months, int days, int years) {
        this.yearMonth = YearMonth.of(years, months);
        this.months = months;
        this.days = days;
        this.years = years;

        /** Helper methods determine if the dates are within range */
        if (validYears(years) && validMonths(months) && validDays(days)) {

            /** Determines if the date is actually valid */
            if (!isDateValid(months, days, years)) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /*****************************************************************
     Constructor.
     @param geoDate (required) String that follows MM/DD/YYYY.
     @throws IllegalArgumentException if the date is invalid
    *****************************************************************/
    public GeoCountDownTimer(String geoDate) {

        /** Date must contain 3 arguments, month, day and year */
        String[] splitter = geoDate.split("/");

        /**
         If our spliiter is anything but the day, month and year,
         then we know that the date is invalid.
         */
        if (splitter.length != 3) {
            throw new IllegalArgumentException();
        }

        /** If we can't parse the parameters, catch an error */
        try {

            /** Trying to parse month string data. */
            int months = Integer.parseInt(splitter[0]);

            /** Trying to parse day string data. */
            int days = Integer.parseInt(splitter[1]);

            /** Trying to parse year string data. */
            int years = Integer.parseInt(splitter[2]);

            this.yearMonth = YearMonth.of(years, months);
            this.months = months;
            this.days = days;
            this.years = years;

            /** Helper methods determine if the dates are within range */
            if (validYears(years) && validMonths(months) && validDays(days)) {

                /** Determines if the date is actually valid */
                if (!isDateValid(months, days, years)) {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch(NumberFormatException e) {
            System.out.println("Error converting integer into string!");
        }
    }

    /*****************************************************************
     Constructor that takes all the elements of 'this'
     GeoCountDownTimer and replaces them with the 'other'
     GeoCountDownTimer.
     @param other the GeoCountDownTimer that is taking place of the
     'this' GeoCountDownTimer
     *****************************************************************/
    public GeoCountDownTimer(GeoCountDownTimer other) {
        this.months = other.getMonths();
        this.days = other.getDays();
        this.years = other.getYears();
    }

    /*****************************************************************
     Main method that tests our software, and runs the user interface
     to go along with it.
     @param args arguments put into the launch options of the program
     @return none
    *****************************************************************/
    public static void main(String[] args) {
        GeoCountDownTimer s = new GeoCountDownTimer("2/10/2018");
        System.out.println("Date: " + s);

        GeoCountDownTimer s1 = new GeoCountDownTimer(2, 20, 2016);
        System.out.println("Date: " + s1.toDateString());

        s1.inc(365);
        System.out.println("Date: " + s1);

        GeoCountDownTimer s2 = new GeoCountDownTimer(4, 10, 2016);
        for (int i = 0; i < (366 + 365 + 365 + 365); i++)
            s2.inc();
        System.out.println("Date: " + s2);

        GeoCountDownTimer s3 = new GeoCountDownTimer("2/1/2016");
        s3.inc(29);
        System.out.println("Date: " + s3);

        GeoCountDownTimer s4 = new GeoCountDownTimer("12/31/2018");
        s4.inc(366);
        System.out.println("Date: " + s4.toDateString());
        s4.inc(365);
        System.out.println("Date: " + s4.toDateString());
        s4.dec(365);
        System.out.println("Date: " + s4.toDateString());
        s4.dec(366);
        System.out.println("Date: " + s4.toDateString());

        GeoCountDownTimer s5 = new GeoCountDownTimer(1, 1, 2060);
        System.out.println("Date: " + s5);
        s5.inc(366);
        System.out.println("Date: " + s5);
        s5.dec(366 + 365);
        System.out.println("Date: " + s5);

        if (s5.equals(s4)) {
            System.out.println("They are equal!");
        } else {
            System.out.println("They are NOT equal!");
        }

        s5 = s4;

        if (s5.equals(s4)) {
            System.out.println("They are equal!");
        } else {
            System.out.println("They are NOT equal!");
        }

        if (s3.compareTo(s2) == 0) {
            System.out.println("They are equal!");
        } else if (s3.compareTo(s2) == -1) {
            System.out.println("s3 < s2");
        } else if (s3.compareTo(s2) == 1) {
            System.out.println("s3 > s2");
        }

        GeoCountDownTimer s6 = new GeoCountDownTimer("9/7/2018");
        System.out.println(s6.daysToGo("9/7/2017"));

        /** Create an instance of our user interface */
        new MyTimerPanel();
    }

    /*****************************************************************
     Method that tests the equality of 'this' GeoCountDownTimer and
     the 'other' GeoCountDownTimer.
     @param other the object that is being tested if it is equal to 'this'
     GeoCountDownTimer and it's elements.
     @return true if they are equal, false if the 'other' object is
     null or not equal to the 'this' object.
    *****************************************************************/
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        GeoCountDownTimer that = (GeoCountDownTimer) other;

        if (months != that.months) {
            return false;
        }

        if (days != that.days) {
            return false;
        }

        if (years != that.years) {
            return false;
        }

        /**
         Ternary function that turns 3-4 if statements into one concise
         return statement.
         */
        boolean result = yearMonth != null ?
                yearMonth.equals(that.yearMonth) : that.yearMonth == null;

        return result;
    }

    /*****************************************************************
     Method that compares two GeoCountDownTimers and tells which
     timer is greater.
     @param other The 'other' GeoCountDownTimer object to compare to
     the 'this' GeoCountDownTimer
     @return 1 if 'this' GeoCountDownTimer is greater, 0 if they are
     equal, and -1 if the 'other' GeoCountDownTimer is equal.
    *****************************************************************/
    public int compareTo(GeoCountDownTimer other) {
        if (this.years == other.getYears()) {
            if (this.months == other.getMonths()) {
                if (this.days == other.getDays()) {
                    return 0;
                } else {
                    return (this.days > other.getDays() ? 1 : -1);
                }
            } else {
                return (this.months > other.getMonths() ? 1 : -1);
            }
        } else {
            return (this.years > other.getYears() ? 1 : -1);
        }
    }

    /*****************************************************************
     Method that decreases the 'this' GeoCountDownTimer object by a
     certain number of days specified.
     @param days number of days to decrease the GeoCountDownTimer by
     @return none
    *****************************************************************/
    public void dec(int days) {
        for (int i = 0; i < days; i++) {

            if (this.days == 1) {

                /** This will hold data for our new month */
                YearMonth newMonth;
                if (this.months == 1) {

                    /**
                     Decrease the year if the day and month is equal to 1
                     */
                    newMonth = YearMonth.of(this.years - 1, 12);
                    this.years--;
                } else {

                    /** Decrease the month if the day is equal to 1 */
                    newMonth = YearMonth.of(this.years, this.months - 1);
                }

                /** Make our new variables */
                int maxDays = newMonth.lengthOfMonth();
                this.days = maxDays;
                this.months = newMonth.getMonthValue();
                this.yearMonth = newMonth;
            } else {
                this.days--;
            }
        }
    }

    /*****************************************************************
     Method that decreases the 'this' GeoCountDownTimer object by 1
     day. Utilizes the dec(int days) method.
     @return none.
    *****************************************************************/
    public void dec() {
        dec(1);
    }

    /*****************************************************************
     Method that increases the 'this' GeoCountDownTimer object by a
     certain number of days specified.
     @param days number of days to increase the GeoCountDownTimer by.
     @return none.
    *****************************************************************/
    public void inc(int days) {
        for (int i = 0; i < days; i++) {

            if (this.days + 1 > this.yearMonth.lengthOfMonth()) {

                /** This will hold data for our new month */
                YearMonth newMonth;
                if (this.months == 12) {

                    /**
                     Increase the year if the month is 12 and it's the
                     last day.
                     */
                    newMonth = YearMonth.of(this.years + 1, 1);
                    this.years++;
                } else {

                    /** Increase the month by 1 */
                    newMonth = YearMonth.of(this.years, this.months + 1);
                }
                this.days = 1;
                this.months = newMonth.getMonthValue();
                this.yearMonth = newMonth;
            } else {
                this.days++;
            }
        }
    }

    /*****************************************************************
     Method that increases the 'this' GeoCountDownTimer object by 1
     day. Utilizes the inc(int days) method.
     @return none.
    *****************************************************************/
    public void inc() {
        inc(1);
    }

    /*****************************************************************
     Method that returns the 'this' GeoCountDownTimer object.
     @return the 'this' GeoCountDownTimer object as Month DD, YYYY.
    *****************************************************************/
    public String toString() {

        /**
         We put the month string into a variable to make it more
         easily accessible.
         */
        String monthString = yearMonth.getMonth().toString();

        return  monthString + " " + this.days + ", " + this.years;
    }

    /*****************************************************************
     Method that returns the 'this' GeoCountDownTimer object.
     @return the 'this' GeoCountDownTimer object in a MM/DD/YYYY format.
    *****************************************************************/
    public String toDateString() {
        return this.months + "/" + this.days + "/" + this.years;
    }

    /*****************************************************************
     Method that uses the Java 8 LocalDate class to validate the date
     passed into the GeoCountDown timer's parameters.
     @param months Current month of the 'this' GeoCountDownTimer
     @param days Current day of the 'this' GeoCountDownTimer
     @param years Current year of the 'this' GeoCountDownTimer
     @return true if the date is valid, false if the date does not exist.
    *****************************************************************/
    public boolean isDateValid(int months, int days, int years) {

        /**
         LocalDate is Java 8 specific and will throw a DateTimeException if
         the date is not valid. But, we  return false instead of
         printing the stacktrace. Very simple line of code that will
         validate our date!
         */
        try {
            LocalDate.of(years, months, days);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    /*****************************************************************
     Method that tells how many days are left between the fromDate
     and the 'this' GeoCountDownTimer date.
     @param fromDate date to get the amount of days between
     @return integer from the 'this' GeoCountDownTimer and the fromDate
     @throws IllegalArgumentException if the fromDate is larger than
     the 'this' GeoCountDownTimer.
    *****************************************************************/
    public int daysToGo(String fromDate) {

        /**
         Creating instance of timer to countdown to, as this will
         also validate our date, and allow us to access the getter
         methods.
         */
        GeoCountDownTimer countdownTimer = new GeoCountDownTimer(fromDate);

        /** Making a Java 8 object of 'this' date. */
        LocalDate thisDate = LocalDate.of(years, months, days);

        /** Making a Java 8 object of 'countdown' date. */
        LocalDate countdownDate = LocalDate.of(countdownTimer.getYears(),
                countdownTimer.getMonths(), countdownTimer.getDays());

        /**
         For some reason, DAYS.between returns a long. We cast it to
         an int and return the result. Otherwise, it is very robust at
         giving us what we need!
         */
        if ((int) DAYS.between(countdownDate, thisDate) >= 0) {
            return (int) DAYS.between(countdownDate, thisDate);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /*****************************************************************
     Method that takes a file name and saves it in the project
     root directory.
     @param fileName location and fileName of which the file will
     be saved.
     @return none.
    *****************************************************************/
    public void save(String fileName) {

        /** How we will write to a file. Starts null. */
        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            out.println(this.months + "/" + this.days + "/" + this.years);

            /** Must close the stream to prevent memory leaks */
            out.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /*****************************************************************
     Method that takes a file name and loads it's date into the 'this'
     GeoCountDownTimer object.
     @param fileName location and fileName of which the file will be saved.
    *****************************************************************/
    public void load(String fileName) {
        try {

            /** Loads the file through scanner. */
            Scanner fileReader = new Scanner(new File(fileName));

            /** Splits our data by '/' */
            String[] splitter = fileReader.next().split("/");

            /** Trying to parse month string data. */
            int months = Integer.parseInt(splitter[0]);

            /** Trying to parse day string data. */
            int days = Integer.parseInt(splitter[1]);

            /** Trying to parse year string data. */
            int years = Integer.parseInt(splitter[2]);

            this.yearMonth = YearMonth.of(years, months);
            this.months = months;
            this.days = days;
            this.years = years;

            /** Helper methods determine if the dates are within range */
            if (validYears(years) && validMonths(months) && validDays(days)) {

                /** Determines if the date is actually valid */
                if (!isDateValid(months, days, years)) {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }

            System.out.println(this.toString());

            /** Must close the stream to prevent memory leaks */
            fileReader.close();
        } catch(Exception error) {
            System.out.println("File not found");
        }
    }

    /*****************************************************************
     Private helper method that checks if the year is valid as stated
     in the project guidelines.
     @param years current year as an int
     @return true if the date is greater than or equal to 2016,
     and false if the date is less than 2016.
    *****************************************************************/
    private boolean validYears(int years) {
        return (years >= 2016);
    }

    /*****************************************************************
     Private helper method that checks if the month (as an integer)
     is valid.
     @param months current month as an int
     @return true if the month is less than 13 and greater than 0,
     and false if the date is greater than 12 or less than 1.
    *****************************************************************/
    private boolean validMonths(int months) {
        return (months > 0 && months < 13);
    }

    /*****************************************************************
     Private helper method that checks if the day in the current
     month is valid. The month must be validated before this method
     is called.
     @param days current day as an int
     @return true if the current month contains the day specified,
     and false if the current month does not contain it.
    *****************************************************************/
    private boolean validDays(int days) {
        return (YearMonth.of(this.years, this.months).lengthOfMonth() >= days);
    }

    /*****************************************************************
     Returns the current month of the 'this' GeoCountDownTimer
     @return months as an int
****************************************************************/
    public int getMonths() {
        return months;
    }

    /*****************************************************************
     Returns the current day of the 'this' GeoCountDownTimer
     @return days as an int
    *****************************************************************/
    public int getDays() {
        return days;
    }

    /*****************************************************************
     Returns the current year of the 'this' GeoCountDownTimer
     @return years as an int
    *****************************************************************/
    public int getYears() {
        return years;
    }
}
