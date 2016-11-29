package project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/*****************************************************************
 List Engine class that is responsible for maintaining the JTable
 as well as items being removed/added to the linked list.
 @author Kyle Flynn
 @version 1.0
 *****************************************************************/
public class ListEngine extends AbstractTableModel {

    /** Instance of the Doubly Linked List that holds DVDs. */
	private SimpleLinkedList<DVD> listDVDs;

    /** This is the maximum length of columns. */
	private final int MAX_COLUMNS = 5;

    /** String array that contains the names of the columns. */
    private String[] columnNames;

    /*****************************************************************
     Default constructor that initializes the declared variables.
     *****************************************************************/
	public ListEngine() {
		super();
		listDVDs = new SimpleLinkedList<DVD>();
        columnNames = new String[] {"Name", "Title", "Player", "Rented On", "Due Back"};
	}

    /*****************************************************************
     Method that removes the DVD from the JTable at the specified
     index.
     @return The DVD that was removed from the specified index.
     *****************************************************************/
	public DVD remove(int i) {

        /** Instance of the DVD that we are removing. */
        DVD unit = null;

        // We must do a try and catch so that the proper error displays.
        try {
            unit = listDVDs.remove(i);
        } catch (LinkedListOutOfBoundsException e) {
            e.printStackTrace();
        } catch (LinkedListEmptyException e) {
            e.printStackTrace();
        }

        // This line if code is responsible for updating the JTable.
        fireTableRowsDeleted(listDVDs.getSize(), listDVDs.getSize());

        return unit;
	}

    /*****************************************************************
     Method that adds the DVD to the JTable and the linked list.
     *****************************************************************/
	public void add (DVD a) {
		listDVDs.add(a);

        // This line if code is responsible for updating the JTable.
        fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);
	}

    /*****************************************************************
     Method that saves the database as a serialized object to the
     desired output file.
     *****************************************************************/
	public void saveDatabase(String filename) {
		try {

            /** General FOS that takes in a file. */
			FileOutputStream fos = new FileOutputStream(filename);

            /** OutputStream that allows us to write to the file. */
			ObjectOutputStream os = new ObjectOutputStream(fos);

            // Write the serialized object and close the stream.
            os.writeObject(listDVDs);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");

		}
	}

    /*****************************************************************
     Method that loads the database from a given serialized file.
     *****************************************************************/
	public void loadDatabase(String filename) {

        // Must clear the table first so we don't see duplicates.
        clearTable();
		try {

            /** General FIS that takes in a file. */
			FileInputStream fis = new FileInputStream(filename);

            /** InputStream that allows us to read from a file. */
			ObjectInputStream is = new ObjectInputStream(fis);

            // Assign the linked list to the object.
			listDVDs = (SimpleLinkedList<DVD>) is.readObject();

            // Update the JTable.
            fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);

            // Close the file stream.
            is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}

    /*****************************************************************
     Method that saves the database as a text file to the desired
     location.
     *****************************************************************/
	public void saveAsText(String filename) {
		try {

            /** Object that writes the object to the file. */
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename)));

			for (int i = 0; i < listDVDs.getSize(); i++) {
                DVD dvdUnit = listDVDs.get(i);

                /** Holds the current entry into the database. */
                String entry = "";

                entry += dvdUnit.getNameOfRenter() + ",";
                entry += dvdUnit.getTitle() + ",";

                // If we're dealing with a game, insert the player type.
                if (dvdUnit instanceof Game) {
                    entry += ((Game)dvdUnit).getPlayer() + ",";
                }

                entry += DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(dvdUnit.getRentedOn().getTime()) + ",";
                entry += DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(dvdUnit.getDueBack().getTime()) + "";

                // Write out the entry to the file.
				out.println(entry);
			}
			out.close();
		} catch (IOException ex) {
            ex.printStackTrace();
		} catch (LinkedListOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     Method that loads the database from a given text file.
     *****************************************************************/
	public void loadFromText(String filename) {

        // Must clear the table first so we don't see duplicates.
        clearTable();
        try {

            /** Object that reads a stream from the given file. */
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            /** Current line that is being read from the file. */
            String line = "";

            // Basically, while there is a line in the file execute.
            while ((line = reader.readLine()) != null) {

                /** Split the line into columns by a comma. */
                String[] columns = line.split(",");

                /** New instance of a dvd that we are creating. */
                DVD unit;

                /** All of the following declarations are assigning
                 column values to specific variables so that we can
                 create a DVD object. */
                String renter = columns[0];
                String title = columns[1];
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar dateRented = new GregorianCalendar();
                GregorianCalendar dateDue = new GregorianCalendar();

                // If there are only 4 columns, then it's a DVD, otherwise a game.
                if (columns.length > MAX_COLUMNS - 1) {
                    PlayerType player = PlayerType.valueOf(columns[2]);
                    Date rented = df.parse(columns[3]);
                    Date due = df.parse(columns[4]);
                    dateRented.setTime(rented);
                    dateDue.setTime(due);

                    unit = new Game(dateRented, dateDue, title, renter, player);
                } else {
                    Date rented = df.parse(columns[2]);
                    Date due = df.parse(columns[3]);
                    dateRented.setTime(rented);
                    dateDue.setTime(due);

                    unit = new DVD(dateRented, dateDue, title, renter);
                }

                listDVDs.add(unit);

            }

            // Update the JTable.
            fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error in loading db");
        }
    }

    /*****************************************************************
     Private helper method that clears the entire JTable and resets the
     linked list.
     *****************************************************************/
    private void clearTable() {
        for (int i = 0; i < this.getRowCount(); i++) {
            remove(i);
        }
        listDVDs.clear();
    }

    /*****************************************************************
     Overriden method that allows it so that column headers may be
     displayed at the top of the JTable.
     @return The String index by the columns array.
     *****************************************************************/
	@Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /*****************************************************************
     Overriden method that gets the current row count of the JTable.
     @return The current row count of the JTable as an int.
     *****************************************************************/
	@Override
	public int getRowCount() {
		return listDVDs.getSize();
	}

    /*****************************************************************
     Overriden method that gets the number of columns inside of the
     JTable.
     @return The current number of columns of the JTable as an int.
     *****************************************************************/
	@Override
	public int getColumnCount() {
		return MAX_COLUMNS;
	}

    /*****************************************************************
     Overrigen method that gets the value at the specified row and
     column.
     @return The desired Object of a given JTable entry.
     *****************************************************************/
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

        /** New instance of the DVD that we are getting. */
        DVD unit = null;
        try {
            unit = listDVDs.get(rowIndex);
        } catch (LinkedListOutOfBoundsException e) {
            e.printStackTrace();
        }

        // Return the desired object based on the column index.
        if (columnIndex == 0) {
            return unit.getNameOfRenter();
        } else if (columnIndex == 1) {
            return unit.getTitle();
        } else if (columnIndex == 2) {
            if (unit instanceof Game) {
                return ((Game) unit).getPlayer();
            } else {
                return "DVD";
            }
        } else if (columnIndex == 3) {
            return DateFormat.getDateInstance(DateFormat.SHORT)
                    .format(unit.getRentedOn().getTime());
        } else if (columnIndex == 4) {
            return DateFormat.getDateInstance(DateFormat.SHORT)
                    .format(unit.getDueBack().getTime());
        } else {
            return null;
        }

	}
}
