package project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;

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
//		fireIntervalRemoved(this, 0, listDVDs.getSize());
        fireTableRowsDeleted(listDVDs.getSize(), listDVDs.getSize());
		return unit;
	}

	public void add (DVD a) {
		listDVDs.add(a);
//		fireIntervalAdded(this, 0, listDVDs.getSize());
        fireTableRowsInserted(listDVDs.getSize() - 1 , listDVDs.getSize() - 1);
	}

	public DVD get (int i) {
		return listDVDs.get(i);
	}

	public int getSize() {
		return listDVDs.getSize();
	}

	// not used.... but interesting and it does work

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
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (SimpleLinkedList<DVD>) is.readObject();
//			fireIntervalAdded(this, 0, listDVDs.getSize() - 1);
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
			out.println(listDVDs.getSize());
			for (int i = 0; i < listDVDs.getSize(); i++) {
				DVD dvdUnit = listDVDs.get(i);
				out.println(dvdUnit.getClass().getName());
				out.println(DateFormat.getDateInstance(DateFormat.SHORT)
						.format(dvdUnit.getRentedOn().getTime()));
				out.println(DateFormat.getDateInstance(DateFormat.SHORT)
						.format(dvdUnit.getDueBack().getTime()));
				out.println(dvdUnit.getNameOfRenter());
				out.println(dvdUnit.getTitle());

				if (dvdUnit instanceof Game)
					out.println(((Game)dvdUnit).getPlayer());

			}
			out.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public void loadFromText(String filename) {

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
