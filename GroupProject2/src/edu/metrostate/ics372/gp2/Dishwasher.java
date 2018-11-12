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
	private static final int TYPE_DISHWASHER = 1;
	/**
	 * Represents a single washer.
	 * 
	 * @param brand
	 *            the washer's brand
	 * @param model
	 *            the washer's model
	 * @param price
	 *            the washer's price
	 */
	public Dishwasher(String brand, String model, double price) {
		super(brand,model,price,TYPE_DISHWASHER);
	}
}
