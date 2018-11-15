package edu.metrostate.ics372.gp2;

/**
 * The Dryer class is used to manage a single dryer.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class Dryer extends ClothesAppliance {
	private static final long serialVersionUID = 1L;

	/**
	 * Represents a single dryer.
	 * 
	 * @param brand
	 *            the dryer's brand
	 * @param model
	 *            the dryer's model
	 * @param price
	 *            the dryer's price
	 */
	public Dryer(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_DRYER);
	}
}
