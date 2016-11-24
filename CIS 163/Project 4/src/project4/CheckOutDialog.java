package project4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class CheckOutDialog extends JDialog implements ActionListener {
	
	private JTextField name;
	private JTextField txtDate;
	private JButton okButton;
	private JButton cancelButton;
	private int closeStatus;
	static final int OK = 0;
	static final int CANCEL = 1;
		
	/*********************************************************
	 Instantiate a Custom Dialog as 'modal' and wait for the
	 user to provide data and click on a button.
	 
	 @param parent reference to the JFrame application
	 @param m an instantiated object to be filled with data
	 *********************************************************/
	
	public CheckOutDialog(JFrame parent) {
		// call parent and create a 'modal' dialog
		super(parent, true);
		
		setTitle("Add DVD in the db");
		closeStatus = CANCEL;
		setSize(400,200);
		
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// instantiate and display two text fields
		name = new JTextField(30);
		txtDate = new JTextField(15);
		txtDate.setText("10/17/2007");
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel("Name: "));
		textPanel.add(name);
		textPanel.add(new JLabel("Run Time: "));
		textPanel.add(txtDate);
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
			closeStatus = OK;
			
			int who = Integer.parseInt(name.getText());
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			
			Date d;
			try {
				d = df.parse(txtDate.getText());

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
	public int getCloseStatus(){
		return closeStatus;
	}
	
}

