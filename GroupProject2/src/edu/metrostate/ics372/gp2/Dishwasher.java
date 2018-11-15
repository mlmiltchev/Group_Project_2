package edu.metrostate.ics372.gp2;

/**
 * The Dishwasher class is used to manage a single Dishwasher.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class Dishwasher extends Appliance {
	private static final long serialVersionUID = 1L;

	/**
	 * Represents a single dishwasher.
	 * 
	 * @param brand
	 *            the dishwasher's brand
	 * @param model
	 *            the dishwasher's model
	 * @param price
	 *            the dishwasher's price
	 */
	public Dishwasher(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_DISHWASHER);
	}
}
