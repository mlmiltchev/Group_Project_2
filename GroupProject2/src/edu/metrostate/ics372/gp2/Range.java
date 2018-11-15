package edu.metrostate.ics372.gp2;

/**
 * The Range class is used to manage a single kitchen range.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class Range extends Appliance {
	private static final long serialVersionUID = 1L;

	/**
	 * Represents a single washer.
	 * 
	 * @param brand
	 *            the kitchen range's brand
	 * @param model
	 *            the kitchen range's model
	 * @param price
	 *            the kitchen range's price
	 */
	public Range(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_RANGE);
	}
}
