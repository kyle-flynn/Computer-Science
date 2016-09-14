import org.junit.Test
import project1.GeoCountDownTimer;

import static org.junit.Assert.*;

/*****************************************************************
 GeoCountDownTimer JUnit Test Case.
 @author Kyle Flynn
 @version 1.5
  *****************************************************************/

public class TestGeoCountDownTimer {

/**
 *
 * The following are simple JUnit test cases... After talking with your instructor, create
 * many, many more that shows that your code is functioning correctly.
 *
 */

    /*****************************************************************
     Test method that will test the first type of constructor in the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testConstructor1() {
        GeoCountDownTimer s = new GeoCountDownTimer (5,10,2016);
        assertEquals(s.toDateString(),"5/10/2016");

        /** Here we test our 1st constructor and it's getters */
        GeoCountDownTimer s1 = new GeoCountDownTimer("5/10/2016");
        assertEquals(s1.toDateString(), "5/10/2016");

        GeoCountDownTimer s2 = new GeoCountDownTimer(5,28,2017);
        assertEquals(s2.getYears(), 2017);
        assertEquals(s2.getMonths(), 5);
        assertEquals(s2.getDays(), 28);
        assertEquals(s2.toDateString(), "5/28/2017");

    }

    /*****************************************************************
     Test method that will test the second type of constructor in the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testConstructor2() {
        GeoCountDownTimer s = new GeoCountDownTimer ("5/10/2016");
        assertTrue(s.toDateString().equals("5/10/2016"));

        /** Here we test the 2nd constructor and it's getters */
        GeoCountDownTimer s2 = new GeoCountDownTimer("5/28/2017");
        assertEquals(s2.getYears(), 2017);
        assertEquals(s2.getMonths(), 5);
        assertEquals(s2.getDays(), 28);
        assertEquals(s2.toString(), "MAY 28, 2017");
    }

    /*****************************************************************
     Test method that will test the 3rd constructor. They should throw
     IllegalArgumentExceptions because of invalid input fields.
     *****************************************************************/
    @Test  (expected = IllegalArgumentException.class)
    public void testConstructor3() {

        /**
         All of these should fail because the ints represent
         invalid dates.
         */
        GeoCountDownTimer s1 = new GeoCountDownTimer(
                new GeoCountDownTimer(2, 32, 2016));
        GeoCountDownTimer s2 = new GeoCountDownTimer(
                new GeoCountDownTimer(13, 32, 2016));
        GeoCountDownTimer s3 = new GeoCountDownTimer(
                new GeoCountDownTimer(2, 32, -1));
        GeoCountDownTimer s4 = new GeoCountDownTimer(
                new GeoCountDownTimer(2, -5, 2080));
        GeoCountDownTimer s5 = new GeoCountDownTimer(
                new GeoCountDownTimer(2, 5, 2012));
        GeoCountDownTimer s6 = new GeoCountDownTimer(
                new GeoCountDownTimer(13, 32, 2015));

        /**
         All of these should fail because the string splitter
         shouldn't be able to parse the entered string.
         */
        GeoCountDownTimer s7 = new GeoCountDownTimer(
                new GeoCountDownTimer("2/29/2016"));
        GeoCountDownTimer s8 = new GeoCountDownTimer(
                new GeoCountDownTimer("13/29/2016"));
        GeoCountDownTimer s9 = new GeoCountDownTimer(
                new GeoCountDownTimer("2/-1/2016"));
        GeoCountDownTimer s10 = new GeoCountDownTimer(
                new GeoCountDownTimer("2/-1/2015"));
        GeoCountDownTimer s11 = new GeoCountDownTimer(
                new GeoCountDownTimer("2/-12016"));
        GeoCountDownTimer s12 = new GeoCountDownTimer(
                new GeoCountDownTimer("2/&/2016"));
    }


    /*****************************************************************
     Test method that will test the increment method of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testAddMethod () {
        GeoCountDownTimer s1 = new GeoCountDownTimer (5,10,2016);
        s1.inc(365);
        System.out.println (s1.toDateString().equals("5/10/2017"));
        assertTrue (s1.toDateString().equals("5/10/2017"));

        s1 = new GeoCountDownTimer (5,10,2016);

        for (int i = 0; i < 365; i++)
            s1.inc();
        System.out.println (s1);
        assertTrue (s1.toDateString().equals("5/10/2017"));

        s1 = new GeoCountDownTimer (5,10,2016);

        System.out.println ("Start:" + s1);
        for (int i = 0; i < 31665; i++)
            s1.inc();
        System.out.println ("Middle:" + s1);

        for (int i = 0; i < 31665; i++)
            s1.dec();
        System.out.println ("End: " + s1);

        GeoCountDownTimer s2 = new GeoCountDownTimer(1, 1, 2017);
        s2.dec();
        assertTrue(s2.toDateString().equals("12/31/2016"));
    }

    /*****************************************************************
     Test method that will test the increment method of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testDecMethod() {
        GeoCountDownTimer s1 = new GeoCountDownTimer (5,10,2017);
        s1.dec(365);
        System.out.println (s1.toDateString().equals("5/10/2016"));
        assertTrue (s1.toDateString().equals("5/10/2016"));

        s1 = new GeoCountDownTimer (5,10,2017);

        for (int i = 0; i < 365; i++)
            s1.dec();
        System.out.println (s1);
        assertTrue (s1.toDateString().equals("5/10/2016"));

        s1 = new GeoCountDownTimer (5,10,2017);

        System.out.println ("Start:" + s1);
        for (int i = 0; i < 31665; i++)
            s1.dec();
        System.out.println ("Middle:" + s1);

        for (int i = 0; i < 31665; i++)
            s1.inc();
        System.out.println ("End: " + s1);

        GeoCountDownTimer s2 = new GeoCountDownTimer(1, 1, 2017);
        s2.dec();
        assertTrue(s2.toDateString().equals("12/31/2016"));
    }

    /*****************************************************************
     Test method that will test the first constructor, and check if it
     handles IllegalArgumentExceptions appropriately.
     *****************************************************************/
    @Test (expected = IllegalArgumentException.class)
    public void testContuctor1() {

        /** Invalid date arguments should throw I.A.E. */
        new GeoCountDownTimer(2,-3,-3);
        new GeoCountDownTimer(-1, 1, 1);
        new GeoCountDownTimer(-1, -1, 1);
        new GeoCountDownTimer(-1, -1, -1);
        new GeoCountDownTimer(-1, 13, -1);
        new GeoCountDownTimer(2, 31, 2016);
        new GeoCountDownTimer(2, 24, 2015);
    }

    /*****************************************************************
     Test method that will test the second constructor, and check if it
     handles IllegalArgumentExceptions appropriately.
     *****************************************************************/
    @Test (expected = IllegalArgumentException.class)
    public void testContuctor2() {

        /** Invalid date arguments should throw I.A.E. */
        new GeoCountDownTimer("2,-3/-3");
        new GeoCountDownTimer("2/2/-1");
        new GeoCountDownTimer("2/56//1");
        new GeoCountDownTimer("2//2/-1");
        new GeoCountDownTimer("123/2/1");
        new GeoCountDownTimer("2/&2/6");
        new GeoCountDownTimer("2/31/2016");
    }

    /*****************************************************************
     Test method that will test the equals method inside of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testEqual () {
        GeoCountDownTimer s1 = new GeoCountDownTimer (5,9,3000);
        GeoCountDownTimer s2 = new GeoCountDownTimer (6,1,2016);
        GeoCountDownTimer s3 = new GeoCountDownTimer (5,5,2016);
        GeoCountDownTimer s4 = new GeoCountDownTimer (5,9,3000);

        /** Here we test all possible equals situations */
        assertFalse(s1.equals(s2));
        assertTrue (s1.equals(s4));
        assertFalse(s3.equals(null));
        assertFalse(s1.equals(s3));
        assertFalse(s2.equals(s3));
    }

    /*****************************************************************
     Test method that will test the compareTo method inside of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testCompareTo () {
        GeoCountDownTimer s1 = new GeoCountDownTimer (5,9,2016);
        GeoCountDownTimer s2 = new GeoCountDownTimer (6,01,2016);
        GeoCountDownTimer s3 = new GeoCountDownTimer (5,8,2016);
        GeoCountDownTimer s4 = new GeoCountDownTimer (5,9,2016);

        /** Here we test all compareTo values */
        assertTrue (s2.compareTo(s1) > 0);
        assertTrue (s3.compareTo(s1) < 0);
        assertTrue (s4.compareTo(s1) == 0);

        assertFalse(s2.compareTo(s1) == 0);
        assertFalse(s3.compareTo(s1) == 0);
        assertFalse(s4.compareTo(s1) != 0);

    }

    /*****************************************************************
     Test method that will test the save and load method inside of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testLoadSave () {
        GeoCountDownTimer s1 = new GeoCountDownTimer (5,9,2016);
        GeoCountDownTimer s2 = new GeoCountDownTimer (5,9,2016);

        s1.save("file1");
        s1 = new GeoCountDownTimer (1,1,2016);
        s1.load("file1");
        assertTrue (s2.equals(s1));

        s1.save("file2");
        s2 = new GeoCountDownTimer(6,7,2017);
        assertFalse(s2.equals(s1));

    }

    /*****************************************************************
     Test method that will test the daysToGo method inside of the
     GeoCountDownTimer class.
     *****************************************************************/
    @Test
    public void testDaysToGo () {
        GeoCountDownTimer s1 = new GeoCountDownTimer (2,9,2016);
        assertTrue (s1.daysToGo("2/1/2016") == 8);
        assertTrue (s1.daysToGo("2/8/2016") == 1);
        assertTrue (s1.daysToGo("2/9/2016") == 0);

        s1 = new GeoCountDownTimer (2,9,5015);
        System.out.println (s1.daysToGo("2/9/2016") + " days to go!");
    }

    /*****************************************************************
     Test method that will test the daysToGo method inside of the
     GeoCountDownTimer class. These should throw an
     IllegalArgumentException.
     *****************************************************************/
    @Test (expected = IllegalArgumentException.class)
    public void testDaysToGoErr() {
        GeoCountDownTimer s1 = new GeoCountDownTimer (2,9,2016);
        System.out.println(s1.daysToGo("2/10/2016"));
        System.out.println(s1.daysToGo("2/10/2017"));
        System.out.println(s1.daysToGo("13/10/2015"));
        System.out.println(s1.daysToGo("2/-1/2016"));
        System.out.println(s1.daysToGo("test"));
        System.out.println(s1.daysToGo("2/2.2016"));
        System.out.println(s1.daysToGo("2.2.2016"));
    }

}