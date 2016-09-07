package project1;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by kylef_000 on 8/29/2016.
 */
public class GeoCountDownTimer {

    private YearMonth yearMonth;

    private int months;
    private int days;
    private int years;

    // Default constructor that sets the GeoCountDownTimer to zero.
    private GeoCountDownTimer() {
        this(0, 0, 0);
    }

    // A constructor that initializes the instance variables with the provided values.
    public GeoCountDownTimer(int months, int days, int years) {
        this.yearMonth = YearMonth.of(years, months);
        this.months = months;
        this.days = days;
        this.years = years;

        if (validYears(years) && validMonths(months) && validDays(days)) {
            if (!isDateValid(months, days, years)) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    // A constructor that accepts a string as a parameter with the following format: “5/10/2016” where 5 indicates the month, 10 indicates the day,  and 2016 indicates the year. You can assume the input has no errors  (i.e., a valid set of numbers) contained within.
    public GeoCountDownTimer(String geoDate) {
        String[] splitter = geoDate.split("/");

        if (splitter.length != 3) {
            throw new IllegalArgumentException();
        }

        try {
            int months = Integer.parseInt(splitter[0]);
            int days = Integer.parseInt(splitter[1]);
            int years = Integer.parseInt(splitter[2]);

            this.yearMonth = YearMonth.of(years, months);
            this.months = months;
            this.days = days;
            this.years = years;

            if (validYears(years) && validMonths(months) && validDays(days)) {
                if (!isDateValid(months, days, years)) {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch(NumberFormatException e) {
            System.out.println("Error converting integer into string! Did you enter a valid date?");
        }
    }

    public static void main(String[] args) {
        GeoCountDownTimer s = new GeoCountDownTimer("2/10/2018");
        System.out.println("Date: " + s);

        GeoCountDownTimer s1 = new GeoCountDownTimer(2, 20, 2015);
        System.out.println("Date: " + s1.toDateString());

        s1.inc(365);
        System.out.println("Date: " + s1);

        GeoCountDownTimer s2 = new GeoCountDownTimer(4, 10, 2016);
        for (int i = 0; i < (366 + 365 + 365 + 365); i++)
            s2.inc();
        System.out.println("Date: " + s2);

        // Create many more test cases in this driver method to
        // Prove the class is functioning correctly.

        GeoCountDownTimer s3 = new GeoCountDownTimer("2/1/2016");
        s3.inc(29);
        System.out.println("Date: " + s3);

        GeoCountDownTimer s4 = new GeoCountDownTimer("12/31/2015");
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

        GeoCountDownTimer s6 = new GeoCountDownTimer("9/7/2016");
        System.out.println(s6.daysToGo("9/16/2016"));

    }

    // A constructor that initializes the instance variables with the other GeoCountDownTimer parameter.
    public void GeoCountDownTimer(GeoCountDownTimer other) {
        this.months = other.getMonths();
        this.days = other.getDays();
        this.years = other.getYears();
    }

    // A method that returns true if “this” GeoCountDownTimer object is exactly the same as the other object (Note: you must cast the other object as a GeoCountDownTimer object).


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoCountDownTimer that = (GeoCountDownTimer) o;

        if (months != that.months) return false;
        if (days != that.days) return false;
        if (years != that.years) return false;
        return yearMonth != null ? yearMonth.equals(that.yearMonth) : that.yearMonth == null;
    }

    // A method that returns '1' if our instance is greater than the other, '-1' if it's less, and '0' if it's equal
    public int compareTo(GeoCountDownTimer other) {
        if (this.years == other.getYears()) {
            if (this.months == other.getMonths()) {
                if (this.days == other.getDays()) {
                    return 0;
                } else {
                    return (this.days > other.getDays() ? 1 : -1); // Ternary to save confusion
                }
            } else {
                return (this.months > other.getMonths() ? 1 : -1);
            }
        } else {
            return (this.years > other.getYears() ? 1 : -1);
        }
    }

    // A method that subtracts the number of days from the current GeoCountDownTimer
    public void dec(int days) {
        for (int i = 0; i < days; i++) {
            if (this.days == 1) {
                YearMonth newMonth;
                if (this.months == 1) {
                    newMonth = YearMonth.of(this.years - 1, 12);
                    this.years--;
                } else {
                    newMonth = YearMonth.of(this.years, this.months - 1);
                }
                int maxDays = newMonth.lengthOfMonth();
                this.days = maxDays;
                this.months = newMonth.getMonthValue();
                this.yearMonth = newMonth;
            } else {
                this.days--;
            }
        }
    }

    // A method that subtracts the number of days by 1 from the current GeoCountDownTimer
    public void dec() {
        dec(1);
    }

    // A method that increases the number of days from the current GeoCountDownTimer
    public void inc(int days) {
        for (int i = 0; i < days; i++) {
            if (this.days + 1 > this.yearMonth.lengthOfMonth()) {
                YearMonth newMonth;
                if (this.months == 12) {
                    newMonth = YearMonth.of(this.years + 1, 1);
                    this.years++;
                } else {
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

    // A method that increases the number of days by 1from the current GeoCountDownTimer
    public void inc() {
        inc(1);
    }

    public String toString() {
        // E.G. Februrary 10, 2018
        return yearMonth.getMonth().toString() + " " + this.days + ", " + this.years;
    }

    public String toDateString() {
        // E.G. 2/20/2015 (MM/DD/YYYY)
        return this.months + "/" + this.days + "/" + this.years;
    }

    public boolean isDateValid(int months, int days, int years) {
        try {
            LocalDate.of(years, months, days);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    public int daysToGo(String fromDate) {
        GeoCountDownTimer futureDate = new GeoCountDownTimer(fromDate);
        LocalDate thisDate = LocalDate.of(this.years, this.months, this.days);
        LocalDate laterDate = LocalDate.of(futureDate.getYears(), futureDate.getMonths(), futureDate.getDays());

        return (int) DAYS.between(thisDate, laterDate);
    }

    public void save(String fileName) {

    }

    public void load(String fileName) {

    }

    private boolean validYears(int years) {
        return (years > 1);
    }

    private boolean validMonths(int months) {
        return (months > 0 && months < 13);
    }

    private boolean validDays(int days) {
        return (YearMonth.of(this.years, this.months).lengthOfMonth() >= days);
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }

    public int getYears() {
        return years;
    }
}
