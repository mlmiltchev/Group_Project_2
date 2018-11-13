package edu.metrostate.ics372.gp2;

/**
 * The Fridge class is used to manage a single refrigerator.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class Fridge extends Appliance {
	private static final long serialVersionUID = 1L;
	int capacity;

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
	public Fridge(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_FRIDGE);
		setCapacity();
	}

	private void setCapacity() {
		try {
			this.capacity = Integer.parseInt(getAttribute("Enter Refrigerator Capacity in Liters:\n"));
		} catch (Exception e) {
			System.out.println("Invalid input, please enter a Capacity.");
			setCapacity();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return String.format("%s, Capacity: %d", super.toString(), getCapacity());
	}
}
