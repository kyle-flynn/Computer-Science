package project4;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;
	protected GregorianCalendar rentedOn;
	protected GregorianCalendar dueBack;
	protected String title;
	protected String nameOfRenter; 
	 	
	public double getCost(GregorianCalendar dat) {
		return !isLate(dat) ? 1.2 : 2;
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

	public boolean isLate(GregorianCalendar today) {
		int yearDue = getDueBack().get(GregorianCalendar.YEAR);
		int monthDue = getDueBack().get(GregorianCalendar.MONTH) + 1;
		int dayDue = getDueBack().get(GregorianCalendar.DAY_OF_MONTH);

        String yearTodayRaw = today.get(GregorianCalendar.YEAR) + "";
        int yearToday = Integer.parseInt(yearTodayRaw.substring(2));
        int monthToday = today.get(GregorianCalendar.MONTH) + 1;
        int dayToday = today.get(GregorianCalendar.DAY_OF_MONTH);

        if (yearToday > yearDue) {
            return true;
        }

        if (monthToday > monthDue) {
            return true;
        }

        if (dayToday > dayDue) {
            return true;
        }

        if (yearToday == yearDue && monthToday == monthDue && dayToday == dayDue) {
            return false;
        }

        return false;
	}

}
