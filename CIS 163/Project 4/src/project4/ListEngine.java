package project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ListEngine extends AbstractTableModel {

	private SimpleLinkedList<DVD> listDVDs;

	private final int MAX_COLUMNS = 5;

    private String[] columnNames;

	public ListEngine() {
		super();
		listDVDs = new SimpleLinkedList<DVD>();
        columnNames = new String[] {"Name", "Title", "Player", "Rented On", "Due Back"};
	}

	public DVD remove(int i) {
		DVD unit = listDVDs.remove(i);
        fireTableRowsDeleted(listDVDs.getSize(), listDVDs.getSize());
		return unit;
	}

	public void add (DVD a) {
		listDVDs.add(a);
        fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);
	}

	public void saveDatabase(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");

		}
	}

	/**
	 * Loads (deserializes) the Account objects from the specified file.
	 * @param filename name of the file to load from.
	 */
	public void loadDatabase(String filename) {
        clearTable();
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (SimpleLinkedList<DVD>) is.readObject();
			fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);
			is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}


	public boolean saveAsText(String filename) {
		if (filename.equals("")) {
			return false;
		}

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename)));
			for (int i = 0; i < listDVDs.getSize(); i++) {
                DVD dvdUnit = listDVDs.get(i);

                String entry = "";

                entry += dvdUnit.getNameOfRenter() + ",";
                entry += dvdUnit.getTitle() + ",";

                if (dvdUnit instanceof Game) {
                    entry += ((Game)dvdUnit).getPlayer() + ",";
                }

                entry += DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(dvdUnit.getRentedOn().getTime()) + ",";
                entry += DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(dvdUnit.getDueBack().getTime()) + "";

				out.println(entry);
			}
			out.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public void loadFromText(String filename) {
        clearTable();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = "";

            while ((line = reader.readLine()) != null) {

                String[] columns = line.split(",");
                DVD unit;

                String renter = columns[0];
                String title = columns[1];
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar dateRented = new GregorianCalendar();
                GregorianCalendar dateDue = new GregorianCalendar();

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

            fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error in loading db");
        }
    }

    private void clearTable() {
        for (int i = 0; i < this.getRowCount(); i++) {
            remove(i);
        }
        listDVDs.clear();
    }

	@Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

	@Override
	public int getRowCount() {
		return listDVDs.getSize();
	}

	@Override
	public int getColumnCount() {
		return MAX_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        DVD unit = listDVDs.get(rowIndex);

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
