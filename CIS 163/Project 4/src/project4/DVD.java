package project4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;
	protected GregorianCalendar rentedOn;
	protected GregorianCalendar dueBack;
	protected String title;
	protected String nameOfRenter; 
	 	
	public double getCost(GregorianCalendar dat) {
		int compare = dat.compareTo(this.getDueBack());
        System.out.println("DATE RENTED: " + dat.getTime() + " | DATE DUE: " + getDueBack().getTime());
		return compare <= 0 ? 1.2 : 2;
	}
	
	public DVD() {
	}
	
	public DVD(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name) {
		super();
		this.rentedOn = rentedOn;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
	}
	
	public GregorianCalendar getRentedOn() {
		return rentedOn;
	}
	public void setRentedOn(GregorianCalendar opened) {
		this.rentedOn = opened;
	}
	public GregorianCalendar getDueBack() {
		return dueBack;
	}
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}
}
