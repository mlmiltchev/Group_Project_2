package edu.metrostate.ics372.gp2;

import java.io.Serializable;

/**
 * The BackOrder class represents an order for a specific customer that has been
 * put on hold until the required quantity of washers for the sale are in the
 * inventory.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class BackOrder implements Serializable, IMatchable<String> {
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private Washer washer;
	private int quantity;

	/**
	 * The customer and washer are stored, along with the requested quantity.
	 * 
	 * @param customer
	 *            the customer requesting the purchase
	 * @param washer
	 *            the washer that the customer wants to purchase
	 * @param quantity
	 *            the number of washers requested for purchase
	 */
	public BackOrder(Customer customer, Washer washer, int quantity) {
		this.customer = customer;
		this.washer = washer;
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
	 * Getter for the washer of this back order.
	 * 
	 * @return washer of this back order
	 */
	public Washer getWasher() {
		return washer;
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
