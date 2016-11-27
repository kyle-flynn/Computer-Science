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


public class DialogRentDvd  extends JDialog implements ActionListener {
	
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField DueBackTxt;
	
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	
	private DVD unit;

	/*********************************************************
		 Instantiate a Custom Dialog as 'modal' and wait for the
		 user to provide data and click on a button.

		 @param parent reference to the JFrame application
		 @param d an instantiated object to be filled with data
	 *********************************************************/

	public DialogRentDvd(JFrame parent, DVD d) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		closeStatus = false;

		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Rent a DVD:");
		setPreferredSize(new Dimension(400,200));
		pack();
		setLocationRelativeTo(null);

		unit = d;
		
		// instantiate and display text fields
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6,2));
		
		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe",30);
		textPanel.add(renterTxt);
		
		textPanel.add(new JLabel("Title of DVD:"));
		titleTxt = new JTextField("Avengers",30);
		textPanel.add(titleTxt);
		
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		textPanel.add(new JLabel("Rented on Date: "));
		rentedOnTxt = new JTextField(df.format(date),30);			//
		textPanel.add(rentedOnTxt);
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();
		  
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
		
		setSize(300,300);
		setVisible (true);	
	}
	
	/**************************************************************
		 Respond to either button clicks
		 @param e the action event that was just fired
	 **************************************************************/
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton) e.getSource();
		
		// if OK clicked the fill the object
		if (button == okButton) {
			// save the information in the object
			closeStatus = true;
			
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date daterentedOn, dateDue;
			try {
				daterentedOn = df.parse(rentedOnTxt.getText());
				dateDue = df.parse(DueBackTxt.getText());
				
				GregorianCalendar rentedOn = new GregorianCalendar();
				GregorianCalendar dueBackOn = new GregorianCalendar();

				rentedOn.setTime(daterentedOn);
				dueBackOn.setTime(dateDue);
				
				unit.setRentedOn(rentedOn);
				unit.setDueBack(dueBackOn);
				unit.setNameOfRenter(renterTxt.getText());
				unit.setTitle(titleTxt.getText());
				
			} catch (ParseException e1) {
				System.out.println ("I have unepectly quit, sorry! goodbey");
			}
		}
		
		// make the dialog disappear
		dispose();
	}
	
	/**************************************************************
		 Return a String to let the caller know which button
		 was clicked
		 
		 @return an int representing the option OK or CANCEL
	 **************************************************************/
	public boolean closeOK(){
		return closeStatus;
	}
	
}



