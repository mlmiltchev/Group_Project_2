package edu.metrostate.ics372.gp2;

/**
 * The Washer class is used to manage a single washer.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher, Andrew Siegfried
 * 
 */
public class Washer extends ClothesAppliance {
	private static final long serialVersionUID = 1L;

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
	public Washer(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_WASHER);
	}
}
