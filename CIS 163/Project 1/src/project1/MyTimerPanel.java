package project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*****************************************************************
 MyTimerPanel JPanel.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/

public class MyTimerPanel extends JPanel {

    /** The frame that contains the window */
    private JFrame frame;

    /** JPanel that will contain the start, stop, and reset buttons */
    private JPanel buttonPanel;

    /** JPanel that will ontain our timer JLabels */
    private JPanel timerPanel;

    /** Instance of our GeoCountDownTimer(s) to be used throughout */
    private GeoCountDownTimer geoCountDownTimer1;
    private GeoCountDownTimer geoCountDownTimer2;
    private GeoCountDownTimer geoCountDownTimer3;

    /** Timer object used for keeping track of time*/
    private Timer javaTimer;

    /** Listens on the timer and fires events on time changes */
    private TimerListener timer;

    /** Listens on which button is clicked so we can respond */
    private ButtonListener buttonListener;

    /** Displays the GeoCountDownTimer(s) */
    private JLabel geoTimer1;
    private JLabel geoTimer2;
    private JLabel geoTimer3;

    /** Controls when to start the GeoCountDownTimer */
    private JButton start;

    /** Controls when to stop the GeoCountDownTimer */
    private JButton stop;

    /** Controls when to reset the GeoCountDownTimer */
    private JButton reset;

    /** Private boolean to tell if the GeoCountDownTimer is running */
    private boolean running = false;

    /*****************************************************************
     Default constructor that loads the necessary elements and
     initializes variables.
     *****************************************************************/
    public MyTimerPanel() {

        /** Initializing all of our declared variables */
        frame = new JFrame("Timer") ;
        buttonPanel = new JPanel(new FlowLayout());
        timerPanel = new JPanel(new BorderLayout());
        geoCountDownTimer1 = new GeoCountDownTimer(5,10,2017);
        geoCountDownTimer2 = new GeoCountDownTimer(8,22,2016);
        geoCountDownTimer3 = new GeoCountDownTimer(1,29,2018);
        timer = new TimerListener();
        buttonListener = new ButtonListener();
        geoTimer1 = new JLabel("1. Days to go (5/10/2016): " +
                geoCountDownTimer1, SwingConstants.CENTER);
        geoTimer2 = new JLabel("2. Days to go (5/10/2016): " +
                geoCountDownTimer2, SwingConstants.CENTER);
        geoTimer3 = new JLabel("3. Days to go (5/10/2016): " +
                geoCountDownTimer3, SwingConstants.CENTER);
        start = new JButton("Start");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        running = false;

        /** Timer runs every ms, so we run it 10 times per second */
        javaTimer = new Timer(100, timer);

        /** Setting font of our labels because default font is small */
        geoTimer1.setFont(new Font("Helvetica", Font.PLAIN, 16));
        geoTimer2.setFont(new Font("Helvetica", Font.PLAIN, 16));
        geoTimer3.setFont(new Font("Helvetica", Font.PLAIN, 16));

        /**
         Adding our listener to all buttons, instead of having
         a separate listener for each button.
         */
        start.addActionListener(buttonListener);
        stop.addActionListener(buttonListener);
        reset.addActionListener(buttonListener);

        /** Adding the buttons to the button panel */
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(reset);

        /** Adding the labels to the button panel */
        timerPanel.add(geoTimer1, BorderLayout.NORTH);
        timerPanel.add(geoTimer2, BorderLayout.CENTER);
        timerPanel.add(geoTimer3, BorderLayout.SOUTH);

        /** Controlling the layout of the entire interface */
        setLayout(new BorderLayout());
        add(timerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        /** Setting the frame parameters */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(400, 150));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /** Starts the timer thread */
        javaTimer.start();
    }

    /*****************************************************************
     TimerListener ActionListener.
     @author Kyle Flynn
     @version 1.0
     *****************************************************************/
    private class TimerListener implements ActionListener {

        /*****************************************************************
         Inherited method that is invoked whenever the user does an
         action.
         @param e the action event that is received by the listener.
         *****************************************************************/
        public void actionPerformed(ActionEvent e) {

            /** If the timer is running, constantly decrease the days */
            if (running) {
                if (geoCountDownTimer1.daysToGo("5/10/2016") > 0) {

                    geoTimer1.setText("1. Days to go (5/10/2016): " + geoCountDownTimer1);
                    geoCountDownTimer1.dec();
                } else {
                    geoTimer1.setText("1. Days to go (5/10/2016): " + geoCountDownTimer1);
                }

                if (geoCountDownTimer2.daysToGo("5/10/2016") > 0) {

                    geoTimer2.setText("2. Days to go (5/10/2016): " + geoCountDownTimer2);
                    geoCountDownTimer2.dec();
                } else {
                    geoTimer2.setText("2. Days to go (5/10/2016): " + geoCountDownTimer2);
                }

                if (geoCountDownTimer3.daysToGo("5/10/2016") > 0) {

                    geoTimer3.setText("3. Days to go (5/10/2016): " + geoCountDownTimer3);
                    geoCountDownTimer3.dec();
                } else {
                    geoTimer3.setText("3. Days to go (5/10/2016): " + geoCountDownTimer3);
                }
            }
        }
    }

    /*****************************************************************
     ButtonListener ActionListener.
     @author Kyle Flynn
     @version 1.0
     *****************************************************************/
    private class ButtonListener implements ActionListener {

        /*****************************************************************
         Inherited method that is invoked whenever the user does an
         action.
         @param e the action event that is received by the listener.
         *****************************************************************/
        public void actionPerformed(ActionEvent e) {

            /** Controls if our program should be counting down */
            if (e.getSource() == start) {
                running = true;
            }

            if (e.getSource() == stop) {
                running = false;
            }

            if (e.getSource() == reset) {
                running = false;
                geoCountDownTimer1 = new GeoCountDownTimer(5,10,2017);
                geoTimer1.setText("1. Days to go (5/10/2016): " +
                        geoCountDownTimer1);

                geoCountDownTimer2 = new GeoCountDownTimer(8,22,2016);
                geoTimer2.setText("2. Days to go (5/10/2016): " +
                        geoCountDownTimer2);

                geoCountDownTimer3 = new GeoCountDownTimer(1,29,2018);
                geoTimer3.setText("3. Days to go (5/10/2016): " +
                        geoCountDownTimer3);
            }
        }
    }

}
