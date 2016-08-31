package project1;

import java.sql.Date;

/**
 * Created by kylef_000 on 8/29/2016.
 */
public class GeoCountDownTimer {

    private int month;
    private int day;
    private int year;

    // Default constructor that sets the GeoCountDownTimer to zero.
    private GeoCountDownTimer() {
        this(0, 0, 0);
    }

    // A constructor that initializes the instance variables with the provided values.
    public GeoCountDownTimer(int month, int day, int year) {

    }

    // A constructor that accepts a string as a parameter with the following format: “5/10/2016” where 5 indicates the month, 10 indicates the day,  and 2016 indicates the year. You can assume the input has no errors  (i.e., a valid set of numbers) contained within.
    public GeoCountDownTimer(String geoDate) {
        String[] splitter = geoDate.split("/");
    }

    public static void main(String[] args) {

    }

    // A constructor that initializes the instance variables with the other GeoCountDownTimer parameter.
    public void GeoCountDownTimer() {

    }

    // A method that returns true if “this” GeoCountDownTimer object is exactly the same as the other object (Note: you must cast the other object as a GeoCountDownTimer object).
    @Override
    public boolean equals(Object other) {
        return this == (GeoCountDownTimer) other;
    }

    // A method that returns '1' if our instance is greater than the other, '-1' if it's less, and '0' if it's equal
    public int compareTo(GeoCountDownTimer other) {
        return -1;
    }

    // A method that subtracts the number of days from the current GeoCountDownTimer
    public void dec(int days) {

    }

    // A method that subtracts the number of days by 1 from the current GeoCountDownTimer
    public void dec() {

    }

    // A method that increases the number of days from the current GeoCountDownTimer
    public void inc(int days) {

    }

    // A method that increases the number of days by 1from the current GeoCountDownTimer
    public void inc() {

    }

    public String toString() {
        // E.G. Februrary 10, 2018
        return "";
    }

    public String toDateString() {
        // E.G. 2/20/2015 (MM/DD/YYYY)
        return "";
    }

    // A boolean method that validates the day. It shouldn't be less than 1, or greater than 31
    private boolean isValidDay(int day) {
        if (day < 1 || day > 31) {
            return false;
        } else {
            return true;
        }
    }

    // A boolean method that validates the day. It shouldn't be less than 1, or greater than 12
    private boolean isValidMonth(int month) {
        if (month < 1 || month > 12) {
            return false;
         } else {
            return true;
        }
    }

    // A boolean method that validates the day. It shouldn't be less than 1
    private boolean isValidYear(int year) {
        if (year < 1) {
            return false;
        } else {
            return true;
        }
    }

}
