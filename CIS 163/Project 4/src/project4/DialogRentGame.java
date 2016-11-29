package project4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*****************************************************************
 Class that opens a dialog so that the user can enter in info to
 rent a Game.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class DialogRentGame  extends JDialog implements ActionListener {
	
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField DueBackTxt;
	private JComboBox playerJbox;
	
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	
	private Game unit;

    /*****************************************************************
     Default constructor that initializes all of the variables and adds
     the components to the parent JFrame.
     @param g The current game that we are creating.
     *****************************************************************/
	public DialogRentGame(JFrame parent, Game g) {

        // Call parent and create a 'modal' dialog
		super(parent, true);

		closeStatus = false;

        // Prevent the user from closing the window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Rent a Game:");
        setPreferredSize(new Dimension(400,200));
		pack();
		setLocationRelativeTo(null);
		
		unit = g;

        // Instantiate a JPanel to hold the text fields.
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6,2));

        // Adding the renter's name text field.
        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe",30);
        textPanel.add(renterTxt);

        // Adding the game title text field.
        textPanel.add(new JLabel("Title of DVD:"));
        titleTxt = new JTextField("Avengers",30);
        textPanel.add(titleTxt);

        // Adding the game player type text field.
        textPanel.add(new JLabel("Game System:"));
        playerJbox = new JComboBox(PlayerType.values());
        textPanel.add(playerJbox);

        /** Instance of the date that was entered. */
        Date date = Calendar.getInstance().getTime();

        /** Date format that formats the entered date. */
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        // Adding the rented date text field.
        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(df.format(date),30);			//
        textPanel.add(rentedOnTxt);

        // Incrementing the due date field by one, just a nice touch.
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        // Adding the due date text field.
        textPanel.add(new JLabel("Due Back: "));
        DueBackTxt = new JTextField(df.format(date),15);
        textPanel.add(DueBackTxt);

        getContentPane().add(textPanel, BorderLayout.CENTER);

        // Instantiate and display two buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Make the dialog visible.
		setVisible(true);
	}

    /*****************************************************************
     Inherited method from the ActionListener class that tells when an
     action has been performed, such as a button click.
     @param e The action that was executed.
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        /** Instance of the JButton that as clicked. */
        JButton button = (JButton) e.getSource();

        // If the confirm button was clicked
        if (button == okButton) {

            // Close the window.
            closeStatus = true;

            /** Date format that formats the entered date. */
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            /** Making date instances of the entered dates. */
            Date daterentedOn, dateDue;
            try {

                /* Parsing the dates so that they may be converted into
                GregorianCalendar objects. */

                daterentedOn = df.parse(rentedOnTxt.getText());
                dateDue = df.parse(DueBackTxt.getText());

                GregorianCalendar rentedOn = new GregorianCalendar();
                GregorianCalendar dueBackOn = new GregorianCalendar();

                rentedOn.setTime(daterentedOn);
                dueBackOn.setTime(dateDue);

                // Setting the properties of the DVD.
                unit.setRentedOn(rentedOn);
                unit.setDueBack(dueBackOn);
                unit.setNameOfRenter(renterTxt.getText());
                unit.setPlayer((PlayerType) playerJbox.getSelectedItem());
                unit.setTitle(titleTxt.getText());

            } catch (ParseException e1) {
                System.out.println ("I have unepectly quit, sorry! goodbey");
            }
        }

        // Dispose of the dialog
        dispose();
    }

    /*****************************************************************
     Getter method that determines whether or not the window has been
     closed.
     @return true if okay, and false if still open.
     *****************************************************************/
	public boolean closeOK(){
		return closeStatus;
	}
	
}



