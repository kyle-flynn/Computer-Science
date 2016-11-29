package project4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;


/*****************************************************************
 GUI for the Rental Store. Core function class of the entire
 program.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class GUIRentalStore implements ActionListener {

    /** Main instance of our JFrame that controls the GUI. */
    private JFrame frame;

    /** Main instance of the menu bar. */
    private JMenuBar menus;

    /** Menu bar instances that display the options. */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /** Menu items that are to be displayed under the file menu. */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;

    /** Menu items that are to be displayed under the action menu. */
    private JMenuItem rentDvdItem;
    private JMenuItem rentGameItem;
    private JMenuItem returnItem;

    /** Main instance of the JTable that holds our data entries. */
    private JTable jTableArea;

    /** Engine that is responsible for managing data entries. */
    private ListEngine dList;

    /*****************************************************************
     Default constructor that initializes the declared variables.
     *****************************************************************/
    public GUIRentalStore() {
        frame = new JFrame();

        fileMenu = new JMenu("File");

        openSerItem = new JMenuItem("Open Serial...");
        saveSerItem = new JMenuItem("Save Serial...");
        exitItem = new JMenuItem("Exit!");
        openTextItem = new JMenuItem("Open Text...");
        saveTextItem = new JMenuItem("Save Text...");
        actionMenu = new JMenu("Action");
        rentDvdItem = new JMenuItem("Rent DVD");
        rentGameItem = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");

        menus = new JMenuBar();
        dList = new ListEngine();

        jTableArea = new JTable(dList);
    }

    /*****************************************************************
     Method that initializes parameters of our initialized variables.
     *****************************************************************/
    public void init() {

        // Adds menu items to the file menu.
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Adds menu items to the action menu.
        actionMenu.add(rentDvdItem);
        actionMenu.add(rentGameItem);
        actionMenu.addSeparator();
        actionMenu.add(returnItem);
        actionMenu.addSeparator();

        // Adding action listeners to all of our menu items.
        rentDvdItem.addActionListener(this);
        rentGameItem.addActionListener(this);
        returnItem.addActionListener(this);
        openSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);

        // Adding the file menus to the menu bar.
        menus.add(fileMenu);
        menus.add(actionMenu);

        // Setting the parameters for the JFrame.
        frame.setJMenuBar(menus);
        frame.add(new JScrollPane(jTableArea));
        frame.setTitle("Blue Box Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*****************************************************************
     Static method that is responsible for launching the rental store
     GUI class.
     *****************************************************************/
    public static void main(String[] args) {
        GUIRentalStore GUI = new GUIRentalStore();
        GUI.init();
    }

    /*****************************************************************
     Inherited method from the ActionListener class that tells when an
     action has been performed, such as a menu item click.
     @param e The action that was executed.
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        /** General JComponent that was called. */
        JComponent comp = (JComponent) e.getSource();

        // Close the application
        if (exitItem == comp) {
            System.exit(0);
        }

        // Open a desired file, whether it's serialized or not.
        if (openSerItem == comp || openTextItem == comp) {

            /** Responsible for displaying the file system. */
            JFileChooser chooser = new JFileChooser();

            /** Controls whether or not the load was successful. */
            int status = chooser.showOpenDialog(null);

            if (status == JFileChooser.APPROVE_OPTION) {

                /** File name of the file that was just loaded. */
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp) {
                    dList.loadDatabase(filename);
                }
                if (openTextItem == e.getSource()) {
                    dList.loadFromText(filename);
                }
            }
        }

        // Save the current database, whether it's serialized or not.
        if (saveSerItem == comp || saveTextItem == comp) {

            /** Responsible for displaying the file system. */
            JFileChooser chooser = new JFileChooser();

            /** Controls whether or not the load was successful. */
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {

                /** File name of the file that was just saved. */
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource()) {
                    dList.saveDatabase(filename);
                }
                if (saveTextItem == e.getSource()) {
                    dList.saveAsText(filename);
                }
            }
        }

        // Open the rent DVD dialog and add it to the list.
        if (rentDvdItem == comp) {

            /** New instance of the DVD that we are creating. */
            DVD dvd = new DVD();

            /** Instance of the dialog that we are creating. */
            DialogRentDvd x = new DialogRentDvd(frame, dvd);
            if (x.closeOK()) {
                dList.add(dvd);
            }
        }

        // Open the rent game dialog and add it to the list.
        if (rentGameItem == comp) {

            /** New instance of the DVD that we are creating. */
            Game game = new Game();

            /** Instance of the dialog that we are creating. */
            DialogRentGame x = new DialogRentGame(frame, game);
            if (x.closeOK()) {
                dList.add(game);
            }
        }

        // Return the selected item and display the cost.
        if (returnItem == e.getSource()) {
            int index = jTableArea.getSelectedRow();
            if (index != -1) {

                /** Instance of the current date (today's date). */
            	GregorianCalendar dat = new GregorianCalendar();

                /** Instance of the DVD that we are removing based on the selection index. */
				DVD unit = dList.remove(index);

                // Display information on the returned DVD.
				JOptionPane.showMessageDialog(null, "Thanks " + unit.getNameOfRenter() +
						"\n for returning " + unit.getTitle() + ", you owe: " + unit.getCost(dat) +
						" dollars");

            }
        }
    }
}


