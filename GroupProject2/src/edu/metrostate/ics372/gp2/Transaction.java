package edu.metrostate.ics372.gp2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Transaction class is used to manage a single transaction for a appliance.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String applianceId;
	private Calendar date;

	/**
	 * Creates the transaction with a given type and a appliance ID. The date is
	 * the current date.
	 * 
	 * @param type
	 *            the type of transaction
	 * @param applianceId
	 *            the ID of the appliance
	 * 
	 */
	public Transaction(String type, String applianceId) {
		this.type = type;
		this.applianceId = applianceId;
		date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
	}

	/**
	 * Returns the type of transaction.
	 * 
	 * @return the type of transaction
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the appliance ID.
	 * 
	 * @return appliance ID
	 */
	public String getApplianceId() {
		return applianceId;
	}

	/**
	 * Returns the date as a String.
	 * 
	 * @return date with month, day, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
	}

	/**
	 * String form of the transaction.
	 * 
	 */
	@Override
	public String toString() {
		return (type + "   " + applianceId);
	}
}
