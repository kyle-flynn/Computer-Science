package project4;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListEngine extends AbstractListModel {	

	private LinkedList<DVD> listDVDs;

	public ListEngine() {
		super();
		listDVDs = new LinkedList<DVD>();
	}

	public DVD remove(int i) {
		DVD unit = listDVDs.remove(i);
		fireIntervalRemoved(this, 0, listDVDs.size());
		return unit;
	}

	public void add (DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	public DVD get (int i) {
		return listDVDs.get(i);
	}

	public Object getElementAt(int arg0) {	

		//	return "Happy";

		DVD unit = listDVDs.get(arg0);

		//return unit; //.getNameOfRenter();

		String dueDateStr = DateFormat.getDateInstance(DateFormat.SHORT)
				.format(unit.getDueBack().getTime());

		String rentedOnDateStr = DateFormat.getDateInstance(DateFormat.SHORT)
				.format(unit.getRentedOn().getTime());

		String line = "Name: " + " " + listDVDs.get(arg0).getNameOfRenter() +
				",  Title: " + listDVDs.get(arg0).title +
				",  rentedOn on: " + rentedOnDateStr +
				",  Due back on: " + dueDateStr;

		if (unit instanceof Game)
			line += ", Game Player: " + ((Game)unit).getPlayer();

		return line;
	}

	public int getSize() {
		return listDVDs.size();
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

			listDVDs = (LinkedList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
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
			out.println(listDVDs.size());
			for (int i = 0; i < listDVDs.size(); i++) {
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
}
