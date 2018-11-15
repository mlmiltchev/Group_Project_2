package edu.metrostate.ics372.gp2;

import java.io.Serializable;

/**
 * The BackOrder class represents an order for a specific customer that has been
 * put on hold until the required quantity of appliances for the sale are in the
 * inventory.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher, Andrew Siegfried
 * 
 */
public class BackOrder implements Serializable, IMatchable<String> {
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private Appliance appliance;
	private int quantity;

	/**
	 * The customer and appliance are stored, along with the requested quantity.
	 * 
	 * @param customer
	 *            the customer requesting the purchase
	 * @param appliance
	 *            the appliance that the customer wants to purchase
	 * @param quantity
	 *            the number of appliances requested for purchase
	 */
	public BackOrder(Customer customer, Appliance appliance, int quantity) {
		this.customer = customer;
		this.appliance = appliance;
		this.quantity = quantity;
	}

	/**
	 * Getter for the customer.
	 * 
	 * @return customer of this back order
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Getter for the appliance of this back order.
	 * 
	 * @return appliance of this back order
	 */
	public Appliance getAppliance() {
		return appliance;
	}

	/**
	 * Getter for the quantity of this back order.
	 * 
	 * @return quantity of this back order
	 */
	public int getQuantity() {
		return quantity;
	}

	@Override
	public boolean matches(String key) {
		if (this.customer.getId().equals(key)) {
			return true;
		} else {
			return false;
		}
	}
}
