package edu.metrostate.ics372.gp2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * The Customer class is used to manage a single customer.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class Customer implements Serializable, IMatchable<String> {
	private static final long serialVersionUID = 1L;
	private static final String MEMBER_STRING = "C";
	private String name;
	private String phoneNumber;
	private String id;
	private List<Appliance> appliancesSold = new LinkedList<Appliance>();
	private List<BackOrder> appliancesOnBackOrder = new LinkedList<BackOrder>();
	private List<Transaction> transactions = new LinkedList<Transaction>();

	/**
	 * Represents a single customer.
	 * 
	 * @param name
	 *            the customer's name
	 * @param phoneNumber
	 *            the customer's phone number
	 */
	public Customer(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		id = MEMBER_STRING + (CustomerIdServer.instance()).getId();
	}

	/**
	 * Getter for the customer's name.
	 * 
	 * @return customer name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for the customer's phone number.
	 * 
	 * @return customer phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Getter for ID
	 * 
	 * @return customer ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for the customer's name.
	 * 
	 * @param name
	 *            customer's new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for the customer's phone number.
	 * 
	 * @param phoneNumber
	 *            customer's new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Stores the appliance as purchased for this customer.
	 * 
	 * @param nextAppliance
	 *            the appliance to be sold
	 * @return true if the appliance could be marked as sold
	 */
	public boolean purchase(Appliance nextAppliance) {
		if (appliancesSold.add(nextAppliance)) {
			transactions.add(new Transaction("Appliance sold: ", nextAppliance.getId()));
			return true;
		}
		return false;
	}

	/**
	 * Places a back order for the appliance.
	 * 
	 * @param hold
	 *            the appliance to be placed on back order
	 */
	public void placeBackOrder(BackOrder backOrder) {
		transactions.add(new Transaction("Back order placed: ", backOrder.getAppliance().getId()));
		appliancesOnBackOrder.add(backOrder);
	}

	/**
	 * Removes a back order.
	 * 
	 * @param applianceId
	 *            the appliance ID for removing a back order
	 * @return true if the back order could be removed
	 */
	public boolean removeBackOrder(String applianceId) {
		for (ListIterator<BackOrder> iterator = appliancesOnBackOrder.listIterator(); iterator.hasNext();) {
			BackOrder backOrder = (BackOrder) iterator.next();
			String id = backOrder.getAppliance().getId();
			if (id.equals(applianceId)) {
				transactions.add(new Transaction("Back order removed: ", backOrder.getAppliance().getId()));
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean matches(String key) {
		if (this.id.equals(key)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * String form of the customer.
	 * 
	 */
	@Override
	public String toString() {
		return "Customer name: " + name + ", phone number: " + phoneNumber + ", ID: " + id + ".";
	}

	/**
	 * Checks whether the customer is equal to the one with the given id.
	 * 
	 * @param id
	 *            ID of the customer who should be compared
	 * @return true if the member IDs match
	 */
	public boolean equals(String id) {
		return this.id.equals(id);
	}
}
