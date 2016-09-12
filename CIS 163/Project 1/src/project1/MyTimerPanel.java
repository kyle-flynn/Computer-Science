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

    /** Instance of our GeoCountDownTimer to be used throughout */
    private GeoCountDownTimer geoCountDownTimer;

    /** In order for our reset to work, we need an old instance */
    private GeoCountDownTimer oldInstance;

    /** Timer object used for keeping track of time*/
    private Timer javaTimer;

    /** Listens on the timer and fires events on time changes */
    private TimerListener timer;

    /** Listens on which button is clicked so we can respond */
    private ButtonListener buttonListener;

    /** Displays the GeoCountDownTimer */
    private JLabel geoTimer;

    /** Controls when to start the GeoCountDownTimer */
    private JButton start;

    /** Controls when to stop the GeoCountDownTimer */
    private JButton stop;

    /** Controls when to reset the GeoCountDownTimer */
    private JButton reset;

    /** Private boolean to tell if the GeoCountDownTimer is running */
    private boolean running = false;

    public static void main(String[] args) {
        new MyTimerPanel();
    }

    /*****************************************************************
     Default constructor that loads the necessary elements and
     initializes variables.
     *****************************************************************/
    public MyTimerPanel() {

        /** Initializing all of our declared variables */
        frame = new JFrame("Timer") ;
        buttonPanel = new JPanel(new FlowLayout());
        geoCountDownTimer = new GeoCountDownTimer(5,10,2017);
        timer = new TimerListener();
        buttonListener = new ButtonListener();
        geoTimer = new JLabel("1. Days to go (5/10/2016): " +
                geoCountDownTimer.daysToGo("5/10/2016"), SwingConstants.CENTER);
        start = new JButton("Start");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        running = false;

        /** Timer runs every ms, so we run it 10 times per second */
        javaTimer = new Timer(100, timer);

        /** Setting font of our label because default font is small */
        geoTimer.setFont(new Font("Helvetica", Font.PLAIN, 16));

        /** Adding the buttons to the button panel */
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(reset);

        /** Controlling the layout of the entire interface */
        setLayout(new BorderLayout());
        add(geoTimer, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        /** Setting the frame parameters */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(400, 100));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /** Starts the timer thread */
        javaTimer.start();
    }

    private class TimerListener implements ActionListener {

        /*****************************************************************
         Inherited method that is invoked whenever the user does an
         action.
         @param e the action event that is received by the listener.
         *****************************************************************/
        public void actionPerformed(ActionEvent e) {

            /** If the timer is running, constantly decrease the days */
            if (running) {
                if (geoCountDownTimer.daysToGo("5/10/2016") > 0) {
                    int daysToGo = geoCountDownTimer.daysToGo("5/10/2016");
                    geoTimer.setText("1. Days to go (5/10/2016): " + daysToGo);
                    geoCountDownTimer.dec();
                } else {
                    geoTimer.setText("1. Days to go (5/10/2016): 0");
                }
            }
        }
    }

    private class ButtonListener implements ActionListener {

        /*****************************************************************
         Inherited method that is invoked whenever the user does an
         action.
         @param e the action event that is received by the listener.
         *****************************************************************/
        public void actionPerformed(ActionEvent e) {

            /** Controls whether or not our program should be running */
            if (e.getSource() == start) {
                running = true;
            }
            if (e.getSource() == stop) {
                running = false;
            }
            if (e.getSource() == reset) {
                running = false;
                geoCountDownTimer = new GeoCountDownTimer(5,10,2017);
                geoTimer.setText("1. Days to go (5/10/2016): " +
                        geoCountDownTimer.daysToGo("5/10/2016"));
            }
        }
    }

}
